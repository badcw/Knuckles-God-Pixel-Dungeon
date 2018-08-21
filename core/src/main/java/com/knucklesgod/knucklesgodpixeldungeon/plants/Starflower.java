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

package com.knucklesgod.knucklesgodpixeldungeon.plants;

import com.knucklesgod.knucklesgodpixeldungeon.Dungeon;
import com.knucklesgod.knucklesgodpixeldungeon.actors.Actor;
import com.knucklesgod.knucklesgodpixeldungeon.actors.Char;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Bless;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Buff;
import com.knucklesgod.knucklesgodpixeldungeon.items.potions.PotionOfExperience;
import com.knucklesgod.knucklesgodpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Starflower extends Plant {

	{
		image = 11;
	}

	@Override
	public void activate() {
		Char ch = Actor.findChar(pos);

		if (ch != null) Buff.prolong(ch, Bless.class, Bless.DURATION);

		if (Random.Int(5) == 0){
			Dungeon.level.drop(new Seed(), pos).sprite.drop();
		}
	}

	public static class Seed extends Plant.Seed{

		{
			image = ItemSpriteSheet.SEED_STARFLOWER;

			plantClass = Starflower.class;
			alchemyClass = PotionOfExperience.class;
		}
	}
}
