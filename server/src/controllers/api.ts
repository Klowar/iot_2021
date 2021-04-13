import { Request, Response } from "express";
import { query, validationResult } from "express-validator";
import { Song } from "../models/Song";

/**
 * List of API examples.
 * @route GET /api
 */
export const getApi = async (req: Request, res: Response) => {
    try {
        await query("from", "Email is not valid").exists().isNumeric().run(req);
        await query("amount", "Email is not valid").exists().isNumeric().run(req);
        const errors = validationResult(req);

        if (!errors.isEmpty())
            return res.status(422).json(errors);

        const { from, amount } = req.query;
        const songs = await Song.findAll({
            offset: Number(from),
            limit: Number(amount)
        });

        return res.status(200).json({ music: songs });
    } catch (e) {
        return res.status(400).json({ error: e});
    }
};
