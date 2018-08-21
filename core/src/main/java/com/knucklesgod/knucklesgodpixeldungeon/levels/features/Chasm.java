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

package com.knucklesgod.knucklesgodpixeldungeon.levels.features;

import com.knucklesgod.knucklesgodpixeldungeon.Assets;
import com.knucklesgod.knucklesgodpixeldungeon.Badges;
import com.knucklesgod.knucklesgodpixeldungeon.Dungeon;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Bleeding;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Buff;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Cripple;
import com.knucklesgod.knucklesgodpixeldungeon.actors.hero.Hero;
import com.knucklesgod.knucklesgodpixeldungeon.actors.mobs.Mob;
import com.knucklesgod.knucklesgodpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.knucklesgod.knucklesgodpixeldungeon.levels.RegularLevel;
import com.knucklesgod.knucklesgodpixeldungeon.levels.rooms.Room;
import com.knucklesgod.knucklesgodpixeldungeon.levels.rooms.special.WeakFloorRoom;
import com.knucklesgod.knucklesgodpixeldungeon.messages.Messages;
import com.knucklesgod.knucklesgodpixeldungeon.scenes.GameScene;
import com.knucklesgod.knucklesgodpixeldungeon.scenes.InterlevelScene;
import com.knucklesgod.knucklesgodpixeldungeon.sprites.MobSprite;
import com.knucklesgod.knucklesgodpixeldungeon.utils.GLog;
import com.knucklesgod.knucklesgodpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Chasm {

	public static boolean jumpConfirmed = false;
	
	public static void heroJump( final Hero hero ) {
		GameScene.show(
			new WndOptions( Messages.get(Chasm.class, "chasm"),
						Messages.get(Chasm.class, "jump"),
						Messages.get(Chasm.class, "yes"),
						Messages.get(Chasm.class, "no") ) {
				@Override
				protected void onSelect( int index ) {
					if (index == 0) {
						jumpConfirmed = true;
						hero.resume();
					}
				}
			}
		);
	}
	
	public static void heroFall( int pos ) {
		
		jumpConfirmed = false;
				
		Sample.INSTANCE.play( Assets.SND_FALLING );

		Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
		if (buff != null) buff.detach();
		
		if (Dungeon.hero.isAlive()) {
			Dungeon.hero.interrupt();
			InterlevelScene.mode = InterlevelScene.Mode.FALL;
			if (Dungeon.level instanceof RegularLevel) {
				Room room = ((RegularLevel)Dungeon.level).room( pos );
				InterlevelScene.fallIntoPit = room != null && room instanceof WeakFloorRoom;
			} else {
				InterlevelScene.fallIntoPit = false;
			}
			Game.switchScene( InterlevelScene.class );
		} else {
			Dungeon.hero.sprite.visible = false;
		}
	}
	
	public static void heroLand() {
		
		Hero hero = Dungeon.hero;
		
		Camera.main.shake( 4, 1f );

		Dungeon.level.press( hero.pos, hero, true );
		Buff.prolong( hero, Cripple.class, Cripple.DURATION );

		//The lower the hero's HP, the more bleed and the less upfront damage.
		//Hero has a 50% chance to bleed out at 66% HP, and begins to risk instant-death at 25%
		Buff.affect( hero, FallBleed.class).set( Math.round(hero.HT / (6f + (6f*(hero.HP/(float)hero.HT)))));
		hero.damage( Math.max( hero.HP / 2, Random.NormalIntRange( hero.HP / 2, hero.HT / 4 )), new Hero.Doom() {
			@Override
			public void onDeath() {
				Badges.validateDeathFromFalling();
				
				Dungeon.fail( Chasm.class );
				GLog.n( Messages.get(Chasm.class, "ondeath") );
			}
		} );
	}

	public static void mobFall( Mob mob ) {
		mob.die( Chasm.class );
		
		((MobSprite)mob.sprite).fall();
	}
	
	public static class Falling extends Buff {
		
		{
			actPriority = VFX_PRIO;
		}
		
		@Override
		public boolean act() {
			heroLand();
			detach();
			return true;
		}
	}
	
	public static class FallBleed extends Bleeding implements Hero.Doom {
		
		@Override
		public void onDeath() {
			Badges.validateDeathFromFalling();
		}
	}
}
