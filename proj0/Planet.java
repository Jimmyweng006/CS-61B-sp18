public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double gravity = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = xxPos - p.xxPos;
        double dy = yyPos - p.yyPos;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        double r = p.calcDistance(this);

        return (gravity * p.mass * mass) / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double force = this.calcForceExertedBy(p);
        double r = p.calcDistance(this);
        double dx = p.xxPos - xxPos;

        return (force * dx) / r;
    }

    public double calcForceExertedByY(Planet p) {
        double force = this.calcForceExertedBy(p);
        double r = p.calcDistance(this);
        double dy = p.yyPos - yyPos;

        return (force * dy) / r;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double ans = 0.0;

        for (Planet p: allPlanets) {
            if (this.equals(p) == true) continue;
            ans += this.calcForceExertedByX(p);
        }

        return ans;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double ans = 0.0;

        for (Planet p: allPlanets) {
            if (this.equals(p) == true) continue;
            ans += this.calcForceExertedByY(p);
        }
        
        return ans;
    }

    public void update(double dt, double fX, double fY) {
        double accelerationX = fX / mass;
        double accelerationY = fY / mass;

        xxVel = xxVel + dt * accelerationX;
        yyVel = yyVel + dt * accelerationY;

        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
