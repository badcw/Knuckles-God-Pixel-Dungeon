/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.knucklesgod.knucklesgodpixeldungeon.items.rings;

import com.knucklesgod.knucklesgodpixeldungeon.actors.Char;
import com.knucklesgod.knucklesgodpixeldungeon.actors.blobs.Electricity;
import com.knucklesgod.knucklesgodpixeldungeon.actors.blobs.ToxicGas;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Burning;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Charm;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Chill;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Corrosion;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Frost;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Ooze;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Paralysis;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Poison;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Weakness;
import com.knucklesgod.knucklesgodpixeldungeon.actors.mobs.Eye;
import com.knucklesgod.knucklesgodpixeldungeon.actors.mobs.Shaman;
import com.knucklesgod.knucklesgodpixeldungeon.actors.mobs.Warlock;
import com.knucklesgod.knucklesgodpixeldungeon.actors.mobs.Yog;
import com.knucklesgod.knucklesgodpixeldungeon.levels.traps.DisintegrationTrap;
import com.knucklesgod.knucklesgodpixeldungeon.levels.traps.GrimTrap;

import java.util.HashSet;

public class RingOfElements extends Ring {
	
	@Override
	protected RingBuff buff( ) {
		return new Resistance();
	}
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( Burning.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Chill.class );
		RESISTS.add( Frost.class );
		RESISTS.add( Ooze.class );
		RESISTS.add( Paralysis.class );
		RESISTS.add( Poison.class );
		RESISTS.add( Corrosion.class );
		RESISTS.add( Weakness.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );
		
		RESISTS.add( ToxicGas.class );
		RESISTS.add( Electricity.class );
		
		RESISTS.add( Shaman.class );
		RESISTS.add( Warlock.class );
		RESISTS.add( Eye.class );
		RESISTS.add( Yog.BurningFist.class );
	}
	
	public static float resist( Char target, Class effect ){
		if (getBonus(target, Resistance.class) == 0) return 1f;
		
		for (Class c : RESISTS){
			if (c.isAssignableFrom(effect)){
				return (float)Math.pow(0.875, getBonus(target, Resistance.class));
			}
		}
		
		return 1f;
	}
	
	public class Resistance extends RingBuff {
	
	}
}
