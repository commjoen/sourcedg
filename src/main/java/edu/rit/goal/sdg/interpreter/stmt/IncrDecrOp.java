package edu.rit.goal.sdg.interpreter.stmt;

import edu.rit.goal.sdg.statement.Stmt;

public abstract class IncrDecrOp implements Stmt {

    public String x;
    public String op;

    public IncrDecrOp(final String x, final String op) {
	super();
	if (!("++".equals(op) || "--".equals(op))) {
	    throw new IllegalArgumentException("The operator should be either '++' or '--'");
	}
	this.x = x;
	this.op = op;
    }

}