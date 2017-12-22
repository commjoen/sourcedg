package edu.rit.goal.sourcedg.analysis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import com.google.common.collect.Sets;
import edu.rit.goal.sourcedg.graph.Edge;
import edu.rit.goal.sourcedg.graph.EdgeType;
import edu.rit.goal.sourcedg.graph.PDG;
import edu.rit.goal.sourcedg.graph.Vertex;

public class PDGSlicer {

  public static Set<Vertex> backward(final PDG pdg, final Set<Vertex> S) {
    final Set<Vertex> S2 = backwardSlice(pdg, S, Sets.newHashSet(EdgeType.PARAM_OUT));
    final Set<Vertex> result =
        backwardSlice(pdg, S2, Sets.newHashSet(EdgeType.PARAM_IN, EdgeType.CALL));
    result.addAll(S2);
    return result;
  }

  private static Set<Vertex> backwardSlice(final PDG pdg, final Set<Vertex> S,
      final Set<EdgeType> kinds) {
    final Set<Vertex> result = new HashSet<>();
    final Set<Vertex> worklist = new HashSet<>(S);
    while (!worklist.isEmpty()) {
      final Vertex v = next(worklist);
      result.add(v);
      final Set<Vertex> w = incoming(pdg, result, v, kinds);
      worklist.addAll(w);
    }
    return result;
  }

  private static Set<Vertex> incoming(final PDG pdg, final Collection<Vertex> marked,
      final Vertex v, final Set<EdgeType> kinds) {
    final Set<Vertex> result = new HashSet<>();
    final Set<Edge> edges = pdg.incomingEdgesOf(v);
    for (final Edge e : edges) {
      if (!kinds.contains(e.getType()) && !marked.contains(e.getSource()))
        result.add(e.getSource());
    }
    return result;
  }

  public static Set<Vertex> forward(final PDG pdg, final Set<Vertex> S) {
    final Set<Vertex> S2 = forwardSlice(pdg, S, Sets.newHashSet(EdgeType.PARAM_IN, EdgeType.CALL));
    final Set<Vertex> result = forwardSlice(pdg, S2, Sets.newHashSet(EdgeType.PARAM_OUT));
    result.addAll(S2);
    return result;
  }

  private static Set<Vertex> forwardSlice(final PDG pdg, final Set<Vertex> S,
      final Set<EdgeType> kinds) {
    final Set<Vertex> result = new HashSet<>();
    final Set<Vertex> worklist = new HashSet<>(S);
    while (!worklist.isEmpty()) {
      final Vertex v = next(worklist);
      result.add(v);
      final Set<Vertex> w = outgoing(pdg, result, v, kinds);
      worklist.addAll(w);
    }
    return result;
  }

  private static Set<Vertex> outgoing(final PDG pdg, final Collection<Vertex> marked,
      final Vertex v, final Set<EdgeType> kinds) {
    final Set<Vertex> result = new HashSet<>();
    final Set<Edge> edges = pdg.outgoingEdgesOf(v);
    for (final Edge e : edges) {
      if (!kinds.contains(e.getType()) && !marked.contains(e.getTarget()))
        result.add(e.getTarget());
    }
    return result;
  }

  private static Vertex next(final Set<Vertex> S) {
    final Iterator<Vertex> it = S.iterator();
    final Vertex result = it.next();
    it.remove();
    return result;
  }

}