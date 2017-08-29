package edu.rit.goal.sdg.interpreter.stmt.sw;

import edu.rit.goal.sdg.interpreter.stmt.Expr;
import edu.rit.goal.sdg.statement.Stmt;

public class Switch implements Stmt {

    public Expr e;
    public ISwitchBody sb;

    public Switch(final Expr e, final ISwitchBody sb) {
	super();
	this.e = e;
	this.sb = sb;
    }

    @Override
    public String toString() {
	return "switch (" + e + ") {" + sb + "}";
    }

}