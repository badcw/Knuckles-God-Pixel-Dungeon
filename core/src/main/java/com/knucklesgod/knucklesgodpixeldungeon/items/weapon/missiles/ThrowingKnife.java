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

package com.knucklesgod.knucklesgodpixeldungeon.items.weapon.missiles;

import com.knucklesgod.knucklesgodpixeldungeon.actors.Actor;
import com.knucklesgod.knucklesgodpixeldungeon.actors.Char;
import com.knucklesgod.knucklesgodpixeldungeon.actors.hero.Hero;
import com.knucklesgod.knucklesgodpixeldungeon.actors.hero.HeroClass;
import com.knucklesgod.knucklesgodpixeldungeon.actors.mobs.Mob;
import com.knucklesgod.knucklesgodpixeldungeon.items.rings.RingOfSharpshooting;
import com.knucklesgod.knucklesgodpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ThrowingKnife extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.THROWING_KNIFE;
		
		bones = false;
		
	}
	
	@Override
	public int min(int lvl) {
		return 2;
	}
	
	@Override
	public int max(int lvl) {
		return 6;
	}
	
	@Override
	public int STRReq(int lvl) {
		return 9;
	}
	
	private Char enemy;
	
	@Override
	protected void onThrow(int cell) {
		enemy = Actor.findChar(cell);
		super.onThrow(cell);
	}
	
	@Override
	public int damageRoll(Char owner) {
		if (owner instanceof Hero) {
			Hero hero = (Hero)owner;
			if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
				//deals 75% toward max to max on surprise, instead of min to max.
				int diff = max() - min();
				int damage = augment.damageFactor(Random.NormalIntRange(
						min() + Math.round(diff*0.75f),
						max()));
				damage = Math.round(damage * RingOfSharpshooting.damageMultiplier( hero ));
				int exStr = hero.STR() - STRReq();
				if (exStr > 0 && hero.heroClass == HeroClass.HUNTRESS) {
					damage += Random.IntRange(0, exStr);
				}
				return damage;
			}
		}
		return super.damageRoll(owner);
	}
	
	@Override
	protected float durabilityPerUse() {
		return super.durabilityPerUse()*2f;
	}
	
	@Override
	public int price() {
		return 6 * quantity;
	}
}
