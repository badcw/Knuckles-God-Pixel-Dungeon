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
import com.knucklesgod.knucklesgodpixeldungeon.actors.Actor;
import com.knucklesgod.knucklesgodpixeldungeon.actors.Char;
import com.knucklesgod.knucklesgodpixeldungeon.actors.mobs.Mob;
import com.knucklesgod.knucklesgodpixeldungeon.items.Heap;
import com.knucklesgod.knucklesgodpixeldungeon.items.Item;
import com.knucklesgod.knucklesgodpixeldungeon.levels.features.Chasm;
import com.knucklesgod.knucklesgodpixeldungeon.scenes.GameScene;

public class PitfallTrap extends Trap {

	{
		color = RED;
		shape = DIAMOND;
	}

	@Override
	public void activate() {
		Heap heap = Dungeon.level.heaps.get( pos );

		if (heap != null){
			for (Item item : heap.items){
				Dungeon.dropToChasm(item);
			}
			heap.sprite.kill();
			GameScene.discard(heap);
			Dungeon.level.heaps.remove( pos );
		}

		Char ch = Actor.findChar( pos );

		if (ch == Dungeon.hero){
			Chasm.heroFall( pos );
		} else if (ch != null){
			Chasm.mobFall((Mob)ch);
		}
	}

	//TODO these used to become chasms when disarmed, but the functionality was problematic
	//because it could block routes, perhaps some way to make this work elegantly?
}