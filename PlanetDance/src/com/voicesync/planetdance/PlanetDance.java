package com.voicesync.planetdance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.widget.ImageView;

public class PlanetDance  extends ImageView { 
	int i,c;
	int outerPlanet=2, innerPlanet=1, nOuterRots=8;
	float planet1Year,planet2Year, planet1Radius,planet2Radius,
	interval, intervalFactor=120f,
	yBottom,yCenter,xCenter,
	a1,a2,a1Interval,a2Interval,
	r,r1,r2,rStop,
	x1,y1,x2,y2;
	int p1,p2, nOrbits;
	float pi=3.141592f;
	float[]Year={87.969f, 224.701f, 365.256f, 686.980f, 4332.6f, 10759.2f, 30685, 60190, 90465 };
	float[]Orbit={57.91f, 108.21f, 149.60f, 227.92f, 778.57f,  1433.5f, 2872.46f, 4495.1f, 5869.7f};
	String[]Name={"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};
	int[]ColorOrbit={Color.BLACK, Color.BLUE, Color.RED, 0xff008000, 0xff800080, 0xff000080, 0xff800000, 0xff8B0000, 0xffFFA500};
	ColorIndex colIx=new ColorIndex();
	class Preset {
		int p1, p2, no;
		public Preset(int p1, int p2, int no) { this.p1=p1; this.p2=p2; this.no=no; }
	}
	Preset[]preset={
			new Preset(2,1,8), new Preset(3,1,7), new Preset(5,4,7), new Preset(6,5,7), 
			new Preset(4,2,7), new Preset(3,2,8), new Preset(2,0,6)
	};
	Paint paint;

	public PlanetDance(Context context, AttributeSet attrs) {	super(context, attrs); 	init();	}
	public PlanetDance(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); 	init(); }
	public PlanetDance(Context context) {	super(context); init();	}
	
	@Override  protected void onDraw(Canvas canvas) {
		drawDance( canvas,  outerPlanet,  innerPlanet,  nOuterRots);
	}
	public void setPlanets(int outerPlanet, int innerPlanet, int nOuterRots) {
		this.outerPlanet=outerPlanet; this.innerPlanet=innerPlanet; this.nOuterRots=nOuterRots;
		postInvalidate();
	}
	public void setPlanets(int outerPlanet, int innerPlanet) {  setPlanets( outerPlanet,  innerPlanet, 8); }
	public void drawDance(Canvas canvas, int outerPlanet, int innerPlanet, int nOuterRots) { 
		setCenter(canvas);
		if (outerPlanet<innerPlanet) {int tmp=innerPlanet; innerPlanet=outerPlanet; outerPlanet=tmp;}
		
		planet1Year = Year[outerPlanet]; planet1Radius = Orbit[outerPlanet];
		planet2Year = Year[innerPlanet]; planet2Radius = Orbit[innerPlanet];
		interval = planet1Year/intervalFactor; //days

		r1=Math.min(xCenter, yCenter);  //outer radius
		r2=r1*planet2Radius/planet1Radius;  //inner radius
		r=0; rStop=planet1Year * nOuterRots;
		a1=0; a1Interval=2*pi*interval/planet1Year;
		a2=0; a2Interval=2*pi*interval/planet2Year;

		for (; r<rStop; r+=interval) {
			a1-=a1Interval; //angle1
			a2-=a2Interval; //angle2
			x1=r1*Cos(a1); y1=r1*Sin(a1);  //convert polar to rectangular coordinates
			x2=r2*Cos(a2); y2=r2*Sin(a2);

			float cfi=r/interval/intervalFactor;
			c=ColorOrbit[(int)(cfi) % ColorOrbit.length];
			paint.setColor(c);
			canvas.drawLine(x1 + xCenter, y1 + yCenter, x2 + xCenter, y2 + yCenter, paint);
		} 
//		canvas.drawText(Name[outerPlanet] + "-" + Name[innerPlanet], 10, 10, paint);
	}
	private void setCenter(Canvas canvas) {
		Rect re=canvas.getClipBounds();
		yCenter=re.height()/2f-2; xCenter=re.width()/2f-2;		
		intervalFactor=Math.min(xCenter, yCenter)/3;
	}
	public String[] getNames() { return Name; }
	public int getP1() {return p1;}
	public int getP2() {return p2;}
	public int getNorbit() { return nOrbits;	}
	public void setPreset(int ps) { ps %= preset.length; setPlanets(p1=preset[ps].p1, p2=preset[ps].p2, nOrbits=preset[ps].no ); 	}
	private void init() {
		paint=new Paint();
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);		
	}
	private float Cos(float x) {return FloatMath.cos(x);}
	private float Sin(float x) {return FloatMath.sin(x);}
}
