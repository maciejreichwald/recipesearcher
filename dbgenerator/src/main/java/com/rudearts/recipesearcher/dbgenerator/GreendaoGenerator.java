package com.rudearts.recipesearcher.dbgenerator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreendaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(2, "com.rudearts.recipesearcher.data.model.greendao");

        Entity treaty = schema.addEntity("RecipeDb");
        treaty.setDbName("recipe");
        treaty.addIdProperty().autoincrement();
        treaty.addStringProperty("title");
        treaty.addStringProperty("description");
        treaty.addStringProperty("imageUrl");
        treaty.addStringProperty("contentUrl");


        DaoGenerator generator = new DaoGenerator();
        generator.generateAll(schema, "../data/src/main/java");
    }
}
