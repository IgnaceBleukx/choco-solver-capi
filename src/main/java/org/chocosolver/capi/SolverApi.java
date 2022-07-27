package org.chocosolver.capi;

import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.criteria.Criterion;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.ObjectHandle;
import org.graalvm.nativeimage.ObjectHandles;
import org.graalvm.nativeimage.c.function.CEntryPoint;

import java.util.List;

/**
 * C entrypoint API to manipulate Solver objects.
 * @author Dimitri Justeau-Allaire.
 */
public class SolverApi {

    private static final String API_PREFIX = "SolverApi_";

    private static ObjectHandles globalHandles = ObjectHandles.getGlobal();

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "findSolution")
    public static ObjectHandle findSolution(IsolateThread thread, ObjectHandle solverHandler,
                                            ObjectHandle stopArrayHandle) {
        Solver solver = globalHandles.get(solverHandler);
        Criterion[] stop = globalHandles.get(stopArrayHandle);
        Solution solution = solver.findSolution(stop);
        ObjectHandle res = globalHandles.create(solution);
        return res;
    }

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "findAllSolutions")
    public static ObjectHandle findAllSolutions(IsolateThread thread, ObjectHandle solverHandler,
                                                ObjectHandle stopArrayHandle) {
        Solver solver = globalHandles.get(solverHandler);
        Criterion[] stop = globalHandles.get(stopArrayHandle);
        List<Solution> solutions = solver.findAllSolutions(stop);
        ObjectHandle res = globalHandles.create(solutions);
        return res;
    }

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "findOptimalSolution")
    public static ObjectHandle findOptimalSolution(IsolateThread thread, ObjectHandle solverHandler,
                                                   ObjectHandle objectiveVariableHandler, boolean maximize,
                                                   ObjectHandle stopArrayHandle) {
        Solver solver = globalHandles.get(solverHandler);
        IntVar objective = globalHandles.get(objectiveVariableHandler);
        Criterion[] stop = globalHandles.get(stopArrayHandle);
        Solution solution = solver.findOptimalSolution(objective, maximize, stop);
        ObjectHandle res = globalHandles.create(solution);
        return res;
    }

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "findAllOptimalSolutions")
    public static ObjectHandle findAllOptimalSolutions(IsolateThread thread, ObjectHandle solverHandler,
                                                       ObjectHandle objectiveVariableHandler, boolean maximize,
                                                       ObjectHandle stopArrayHandle) {
        Solver solver = globalHandles.get(solverHandler);
        IntVar objective = globalHandles.get(objectiveVariableHandler);
        Criterion[] stop = globalHandles.get(stopArrayHandle);
        List<Solution> solutions = solver.findAllOptimalSolutions(objective, maximize, stop);
        ObjectHandle res = globalHandles.create(solutions);
        return res;
    }

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "showStatistics")
    public static void showStatistics(IsolateThread thread, ObjectHandle solverHandler) {
        Solver solver = globalHandles.get(solverHandler);
        solver.showStatistics();
    }

    @CEntryPoint(name = Constants.METHOD_PREFIX + API_PREFIX + "showShortStatistics")
    public static void showShortStatistics(IsolateThread thread, ObjectHandle solverHandler) {
        Solver solver = globalHandles.get(solverHandler);
        solver.showShortStatistics();
    }
}
