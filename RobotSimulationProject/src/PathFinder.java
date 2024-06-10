import java.awt.*;

public class PathFinder {

/*
    public Point findBestRoute(Point startingPoint, Point target, Point[] obstacles){
        Point[][] points = new Point[3][3];
        for(int i = -1; i < 2; i++){
            for(int x = -1; x < 2; x++){
                points[i+1][x+1] = new Point(startingPoint.x + i, startingPoint.y + x);
            }
        }
    }

    private Point findBestRoute(Point[] visitedPoints, Point target, Point[] obstacles, Point bestNode){

    }
*/
    private double calculateDistance(Point point1, Point point2){
        return (Math.sqrt(Math.pow(point2.getX() - point1.getX(),2) + Math.pow(point2.getY() - point1.getY(), 2)));
    }
}
