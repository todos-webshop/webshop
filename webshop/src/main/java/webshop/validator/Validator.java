package webshop.validator;

public interface Validator {

    default boolean isEmpty(String str){
        return str == null || str.equals("");
    }
}
