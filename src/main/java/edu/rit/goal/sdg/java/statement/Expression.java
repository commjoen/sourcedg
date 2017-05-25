package edu.rit.goal.sdg.java.statement;

import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.tree.ParseTree;

import edu.rit.goal.sdg.java.antlr.Java8Parser.ExpressionNameContext;

public class Expression {

    private ParseTree ast;
    private final Set<String> readingVars;

    public Expression(final ParseTree ast) {
	super();
	this.ast = ast;
	readingVars = getReadingVars(ast);
    }

    public ParseTree getAst() {
	return ast;
    }

    public void setAst(final ParseTree ast) {
	this.ast = ast;
    }

    public Set<String> getReadingVars() {
	return readingVars;
    }

    private String getText() {
	return ast.getText();
    }

    @Override
    public String toString() {
	return getText() + " -> " + readingVars;
    }

    private Set<String> getReadingVars(final ParseTree ctx) {
	final Set<String> result = new HashSet<>();
	if (ctx.getChildCount() == 0) {
	    final ParseTree parent = ctx.getParent();
	    if (parent instanceof ExpressionNameContext) {
		result.add(parent.getText());
		return result;
	    }
	}
	for (int i = 0; i < ctx.getChildCount(); i++) {
	    final ParseTree child = ctx.getChild(i);
	    result.addAll(getReadingVars(child));
	}
	return result;
    }

}