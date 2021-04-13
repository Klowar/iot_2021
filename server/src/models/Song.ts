import { DataTypes } from "sequelize";
import { sequelize } from ".";

export const Song = sequelize.define("song", {
    // Model attributes are defined here
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        defaultValue: 0
    },
    url: {
        type: DataTypes.STRING,
        allowNull: false
    },
    name: {
        type: DataTypes.STRING

    }
}, {
    createdAt: false,
    updatedAt: false,
    version: false,
    tableName: "songs"
});