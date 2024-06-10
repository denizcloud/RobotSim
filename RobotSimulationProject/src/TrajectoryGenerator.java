public class TrajectoryGenerator {
    public static class CubicHermiteSpline{
        //seni yerim kedi
        double startingPoint;
        double finishingPoint;
        double startingVelocity;
        double finishingVelocity;
        double maxAcceleration;
        double maxVelocity;

        double a0;
        double a1;
        double a2;
        double a3;
        double t;


        public CubicHermiteSpline(double startingPoint, double finishingPoint, double startingVelocity, double finishingVelocity, double maxVelocity, double maxAcceleration){
            this.startingPoint = startingPoint;
            this.finishingPoint = finishingPoint;
            this.startingVelocity = startingVelocity;
            this.finishingVelocity = finishingVelocity;
            this.maxVelocity = maxVelocity;
            this.maxAcceleration = maxAcceleration;

            t = 2 * maxVelocity / maxAcceleration + ((finishingPoint - startingPoint)/maxVelocity - maxVelocity/maxAcceleration);

            a0 = startingPoint;
            a1 = startingVelocity;
            a2 = 1/Math.pow(t,2) * (3*finishingPoint - 3 * startingPoint - 2 * startingVelocity * t);
            a3 = (finishingVelocity * t - 2 * finishingPoint + 2 * startingPoint + startingVelocity * t) / Math.pow(t,3);
        }

        public double[] getValues(double t){
            return new double[]{
                    getPosition(t),
                    getVelocity(t),
                    getAcceleration(t)
            };
        }

        public double getMaxTime(){
            return t;
        }

        public double getPosition(double t){
            return (a0 + a1 * t + a2 * Math.pow(t,2) + a3 * Math.pow(t,3));
        }

        public double getVelocity(double t){
            return (a1 + a2 * t * 2 + a3 * 3 * Math.pow(t,2));
        }

        public double getAcceleration(double t){
            return (a2 + 6 * a3 * t);
        }
    }
}
