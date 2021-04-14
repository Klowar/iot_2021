import bodyParser from "body-parser";
import compression from "compression"; // compresses requests
import express from "express";
import flash from "express-flash";
import lusca from "lusca";
import path from "path";
// Controllers (route handlers)
import * as apiController from "./controllers/api";

// Create Express server
const app = express();

// Express configuration
app.set("port", process.env.PORT || 3000);
app.use(compression());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(flash());
app.use(lusca.xssProtection(true));


/**
 * API examples routes.
 */
app.get("/api", apiController.getApi);

export default app;
