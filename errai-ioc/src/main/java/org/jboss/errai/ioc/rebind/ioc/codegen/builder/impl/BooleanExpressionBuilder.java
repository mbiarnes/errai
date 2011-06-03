package org.jboss.errai.ioc.rebind.ioc.codegen.builder.impl;

import org.jboss.errai.ioc.rebind.ioc.codegen.BooleanOperator;
import org.jboss.errai.ioc.rebind.ioc.codegen.Context;
import org.jboss.errai.ioc.rebind.ioc.codegen.MetaClassFactory;
import org.jboss.errai.ioc.rebind.ioc.codegen.Statement;
import org.jboss.errai.ioc.rebind.ioc.codegen.meta.MetaClass;

/**
 * @author Mike Brock <cbrock@redhat.com>
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public class BooleanExpressionBuilder implements Statement {
    private Statement lhs;
    private String lhsExpr;
    private Statement rhs;
    private BooleanOperator operator;

    public BooleanExpressionBuilder() {}
    
    public BooleanExpressionBuilder(Statement rhs, BooleanOperator operator) {
        this.rhs = rhs;
        this.operator = operator;
    }
    
    public BooleanExpressionBuilder(Statement lhs, Statement rhs, BooleanOperator operator) {
        this(rhs, operator);
        this.lhs = lhs;
    }

    public BooleanExpressionBuilder(String lhsExpr, Statement rhs, BooleanOperator operator) {
        this(rhs, operator);
        this.lhsExpr = lhsExpr;
    }

    public void setLhs(Statement lhs) {
        this.lhs = lhs;
    }

    public void setLhsExpr(String lhsExpr) {
        this.lhsExpr = lhsExpr;
    }
    
    public String generate(Context context) {
        return (lhs != null) ? lhs.generate(context) : lhsExpr + 
                ((operator !=null) ? (" " + operator.getCanonicalString()) : "") + 
                ((rhs !=null) ? (" " + rhs.generate(context)) : "");
    }

    public Context getContext() {
        return null;
    }

    public MetaClass getType() {
        return MetaClassFactory.get(boolean.class);
    }

}
