package ast;

/**
 * Created by joao on 29/09/15.
 */
public class TypeClass extends Type {

    public TypeClass(String name){
        super(name);
    }

    @Override
    public String getCname(){
        return super.getName();
    }
}
