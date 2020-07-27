package sourcedg;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.github.javaparser.utils.Log;
import sourcedg.builder.PDGBuilder;
import sourcedg.builder.PDGBuilderConfig;
import sourcedg.graph.CFG;
import sourcedg.graph.PDG;
import sourcedg.graph.VertexType;
import sourcedg.util.GraphExporter;

public class Test {

	public static void main(final String[] args) throws Exception {
		System.out.println("startin");
		final FileInputStream in = new FileInputStream("/Users/razor/Documents/workspace/sourcedg/src/main/java/sourcedg/Test.java");
		PDGBuilderConfig config = PDGBuilderConfig.create();
		final PDGBuilder builder = new PDGBuilder(config, Level.WARNING);
		builder.build(in);
		final PDG pdg = builder.getPDG();
		pdg.collapseNodes(VertexType.ACTUAL_OUT);
		pdg.collapseNodes(VertexType.ARRAY_IDX);
		System.out.println(pdg);
		final Iterator<CFG> it = builder.getCfgs().iterator();
		final CFG cfg = it.next();
		builder.getCfgs().stream()
				.forEach(cfg1 -> System.out.println(cfg1.getEntry().getAssignment()));
		System.out.println(cfg.getEntry().getLine());
		// System.out.println(cfg.cyclomaticComplexity());
		GraphExporter.exportAsDot(pdg, "/Users/razor/Desktop", "pdg");
	}

}
