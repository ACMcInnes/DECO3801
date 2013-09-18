var Models = {
    ObsticleInfo: null,

    init: function() {
        var distanceFactor = 580.2;

        /* null means: use relative to user, beacon is NORTH to the user */
        var locationbeacon = new AR.RelativeLocation(null, 25000, 0, 5000);

		/* Load all the 3D models */
        var beacon = new AR.Model("assets/Beacon.wt3");
        var cloak = new AR.Model("assets/Cloak.wt3");
        var dog = new AR.Model("assets/Dog.wt3");    			
		var firewall = new AR.Model("assets/Firewall.wt3");
		var guard = new AR.Model("assets/Guard.wt3");
		var hackin = new AR.Model("assets/HackIn.wt3");
		var hackout = new AR.Model("assets/HackOut.wt3");
		var landmine = new AR.Model("assets/LandMine.wt3");
		var motion = new AR.Model("assets/Motion.wt3");

        /* put Traps and Hack Points in an array */
        this.ObsticleInfo = [beacon, cloak, dog , firewall, guard, hackin, hackout, landmine, motion];

        /* create helper array to create goeObjects out of given information */
        var ObsticleGeo = [];
        for (var i = 0; i < this.ObsticleInfo.length; i++) {

            /* show name of object below*/
            var label = new AR.Label(this.ObsticleInfo[i].name, 3, {
                offsetY : -this.ObsticleInfo[i].size/2,
                verticalAnchor : AR.CONST.VERTICAL_ANCHOR.TOP,
                opacity : 0.9,
                zOrder: 1, style : {textColor : '#FFFFFF', backgroundColor : '#00000005'}
                });

            /* drawable in cam of object -> image and label */
            var drawables = [];
            drawables[0] = this.ObsticleInfo[i].imgDrawable;
            drawables[1] = label;

            /* Create objects in AR*/
            ObsticleGeo[i] = new AR.GeoObject(this.ObsticleInfo[i].location, { drawables: { cam: drawables }, onClick: this.modelClicked(this.ObsticleInfo[i]) } );
            if (i > 0) {
                this.animate(this.ObsticleInfo[i]);
            } else {
                var beaconHackAnim = new AR.PropertyAnimation(this.ObsticleInfo[i].location, 'northing', 10000, 10000, 1000, {type:AR.CONST.EASING_CURVE_TYPE.EASE_IN_SINE});
                beaconHackAnim.start(-1);
            }
        }

        // Add indicator to beacon
        var imageDrawable = new AR.ImageDrawable(indicatorImg, 0.1, { verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP });
        ObsticleGeo[0].drawables.addIndicatorDrawable(imageDrawable);
    },

    animate: function(model) {
        var relLocation = model.location;
        var distance = model.distance;
        var roundingTime = distance*2*2;

        var northSouthAnimation1 = new AR.PropertyAnimation(relLocation, 'northing', distance*1, distance*0, roundingTime/4, {type:AR.CONST.EASING_CURVE_TYPE.EASE_IN_SINE});
        var eastWestAnimation1 = new AR.PropertyAnimation(relLocation, 'easting', distance*0, distance*1, roundingTime/4 , {type:AR.CONST.EASING_CURVE_TYPE.EASE_OUT_SINE});

        var northSouthAnimation2 = new AR.PropertyAnimation(relLocation, 'northing', distance*0, distance*-1, roundingTime/4, {type:AR.CONST.EASING_CURVE_TYPE.EASE_OUT_SINE});
        var eastWestAnimation2 = new AR.PropertyAnimation(relLocation, 'easting', distance*1, distance*0, roundingTime/4 , {type:AR.CONST.EASING_CURVE_TYPE.EASE_IN_SINE});

        var northSouthAnimation3 = new AR.PropertyAnimation(relLocation, 'northing', distance*-1, distance*0, roundingTime/4, {type:AR.CONST.EASING_CURVE_TYPE.EASE_IN_SINE});
        var eastWestAnimation3 = new AR.PropertyAnimation(relLocation, 'easting', distance*0, distance*-1, roundingTime/4 , {type:AR.CONST.EASING_CURVE_TYPE.EASE_OUT_SINE});

        var northSouthAnimation4 = new AR.PropertyAnimation(relLocation, 'northing', distance*0, distance*1, roundingTime/4, {type:AR.CONST.EASING_CURVE_TYPE.EASE_OUT_SINE});
        var eastWestAnimation4 = new AR.PropertyAnimation(relLocation, 'easting', distance*-1, distance*0, roundingTime/4 , {type:AR.CONST.EASING_CURVE_TYPE.EASE_IN_SINE});

        var q1 = new AR.AnimationGroup(AR.CONST.ANIMATION_GROUP_TYPE.PARALLEL, [northSouthAnimation1, eastWestAnimation1]);
        var q2 = new AR.AnimationGroup(AR.CONST.ANIMATION_GROUP_TYPE.PARALLEL, [northSouthAnimation2, eastWestAnimation2]);
        var q3 = new AR.AnimationGroup(AR.CONST.ANIMATION_GROUP_TYPE.PARALLEL, [northSouthAnimation3, eastWestAnimation3]);
        var q4 = new AR.AnimationGroup(AR.CONST.ANIMATION_GROUP_TYPE.PARALLEL, [northSouthAnimation4, eastWestAnimation4]);

        var cicularAnimationGroup = new AR.AnimationGroup(AR.CONST.ANIMATION_GROUP_TYPE.SEQUENTIAL, [ q4, q1, q2, q3]);

        cicularAnimationGroup.start(-1);
    },

    modelClicked: function (model) {
        return function() {
            document.getElementById("info").setAttribute("class", "info");
            document.getElementById("name").innerHTML = model.name;
            document.getElementById("info").setAttribute("class", "infoVisible");
        };
    }
};

Models.init();
