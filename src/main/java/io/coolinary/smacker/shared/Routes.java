package io.coolinary.smacker.shared;

public class Routes {

    public static final String ID = "/{id}";
    public static final String PID = "/{publicId}";
    public static final String RECIPE_ID = "/{recipeId}";
    public static final String PRODUCT_ID = "{productId}";

    public static final String ALL = "/all";
    public static final String TOOLS = "/tools";
    public static final String IMAGES = "/images";
    public static final String RECIPES = "/recipes";
    public static final String PRODUCTS = "/products";
    public static final String RECIPE_CATEGORIES = "/recipe-categories";
    public static final String PRODUCT_CATEGORIES = "/product-categories";

    public static final String TOOLS_ALL = TOOLS + ALL;
    public static final String PRODUCTS_ALL = PRODUCTS + ALL;
    public static final String PRODUCT_CATEGORIES_ALL = PRODUCT_CATEGORIES + ALL;
    public static final String RECIPE_CATEGORIES_ALL = RECIPE_CATEGORIES + ALL;

    public static final String RECIPE_PUBLIC_ID = "/{recipePublicId}";
    public static final String RECIPE_CATEGORY_PUBLIC_ID = "/{recipeCategoryPublicId}";

}
