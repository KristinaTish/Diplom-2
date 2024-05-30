package orderpac;

public class SerealCreateOrderRequest {
    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    private String[] ingredients;


    public SerealCreateOrderRequest(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public static SerealCreateOrderRequest someIngreds (){
        String[] ingreds = {"61c0c5a71d1f82001bdaaa79", "61c0c5a71d1f82001bdaaa7a"};
        return new SerealCreateOrderRequest(ingreds);
    }
}


