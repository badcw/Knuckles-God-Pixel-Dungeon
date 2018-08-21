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

package com.knucklesgod.knucklesgodpixeldungeon.levels.traps;

import com.knucklesgod.knucklesgodpixeldungeon.Dungeon;
import com.knucklesgod.knucklesgodpixeldungeon.actors.hero.Belongings;
import com.knucklesgod.knucklesgodpixeldungeon.items.Item;
import com.knucklesgod.knucklesgodpixeldungeon.items.artifacts.LloydsBeacon;
import com.knucklesgod.knucklesgodpixeldungeon.journal.Notes;
import com.knucklesgod.knucklesgodpixeldungeon.scenes.InterlevelScene;
import com.watabou.noosa.Game;

public class DistortionTrap extends Trap{

	{
		color = TEAL;
		shape = LARGE_DOT;
	}

	@Override
	public void activate() {
		InterlevelScene.returnDepth = Dungeon.depth;
		Belongings belongings = Dungeon.hero.belongings;
		
		for (Notes.Record rec : Notes.getRecords()){
			if (rec.depth() == Dungeon.depth){
				Notes.remove(rec);
			}
		}
		
		for (Item i : belongings){
			if (i instanceof LloydsBeacon && ((LloydsBeacon) i).returnDepth == Dungeon.depth)
					((LloydsBeacon) i).returnDepth = -1;
		}

		InterlevelScene.mode = InterlevelScene.Mode.RESET;
		Game.switchScene(InterlevelScene.class);
	}
}
