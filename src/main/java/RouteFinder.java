import service.RouteFindingService;
import util.math.RouteFinderConstants;

public class RouteFinder {

    public static void main(String[] args) {
        RouteFindingService.findRoute(getInputFileName(args), RouteFinderConstants.ROUTE_ACCURACY);
    }


    private static String getInputFileName(String[] args) {
        return args.length > 0 ? args[0] : "routes.csv";
    }
}
