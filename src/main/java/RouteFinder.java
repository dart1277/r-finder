import service.RouteFindingService;

public class RouteFinder {

    public static void main(String[] args) {
        RouteFindingService.findRoute(getInputFileName(args));
    }

    private static String getInputFileName(String[] args) {
        return args.length > 0 ? args[0] : "routes.csv";
    }
}
