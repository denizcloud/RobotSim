
public class TestMain {
    public static  void main(String[] args){
        double start = 0;
        TrajectoryGenerator.CubicHermiteSpline spline = new TrajectoryGenerator.CubicHermiteSpline(0,30, 0,0,5,3);
        if (start == 0) start = System.currentTimeMillis();
        System.out.println("milis " + System.currentTimeMillis());
        while((System.currentTimeMillis() - start) /1000.0 < spline.getMaxTime()){
            double time = (System.currentTimeMillis() - start) / 1000.0;
            System.out.println("Spline Point: " + spline.getPosition(time));
            System.out.println("Spline Velocity: " + spline.getVelocity(time));
            System.out.println("Spline Acceleration: " + spline.getAcceleration(time));
        }
    }
}
