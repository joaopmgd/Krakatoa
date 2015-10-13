package ast;

/**
 * Created by joao on 29/09/15.
 */
public class PrivateMethodList extends MethodList {

    public PrivateMethodList(){
        super();
    }

    public void genKra(PW pw) {
        super.genKra(pw, "public");
    }
}
