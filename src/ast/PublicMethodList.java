package ast;

/**
 * Created by joao on 29/09/15.
 */
public class PublicMethodList extends MethodList {

    public void genKra(PW pw){
        super.genKra(pw, "public");
    }

    public PublicMethodList(){
        super();
    }
}
