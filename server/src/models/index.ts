import { Sequelize } from "sequelize";
import { DB_URL } from "../util/secrets";

export const sequelize = new Sequelize(DB_URL); // Example for postgres
