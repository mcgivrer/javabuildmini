package tutorials.core.physic;

public record Material(String name, double friction, double elasticity, double density) {
    public static final Material DEFAULT = new Material("Generic", 0.7, 0.6, 1.0);
    public static final Material ICE = new Material("Ice", 0.1, 0.05, 0.92);
    public static final Material STEEL = new Material("Steel", 0.6, 0.8, 7.8);
    public static final Material WOOD = new Material("Wood", 0.4, 0.2, 0.6);
    public static final Material RUBBER = new Material("Rubber", 0.9, 0.7, 1.5);
    public static final Material GLASS = new Material("Glass", 0.7, 0.3, 2.5);
    public static final Material CONCRETE = new Material("Concrete", 0.65, 0.25, 2.4);
    public static final Material PLASTIC = new Material("Plastic", 0.5, 0.4, 0.95);
}
