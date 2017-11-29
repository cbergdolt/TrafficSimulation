package application.intersection;

public enum LightState {
	GNS_REW,	//green North/South, red East/West 
	YNS_REW,	//yellow North/South, red East/West
	RNS_GEW,	//red North/South, green East/West
	RNS_YEW,	//red North/South, yellow East/West
	NONE		//light has no state, useful if a light instantiation is null
}
