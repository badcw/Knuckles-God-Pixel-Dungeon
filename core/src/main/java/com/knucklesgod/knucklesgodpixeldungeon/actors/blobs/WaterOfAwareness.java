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

package com.knucklesgod.knucklesgodpixeldungeon.actors.blobs;

import com.knucklesgod.knucklesgodpixeldungeon.Assets;
import com.knucklesgod.knucklesgodpixeldungeon.Badges;
import com.knucklesgod.knucklesgodpixeldungeon.Dungeon;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Awareness;
import com.knucklesgod.knucklesgodpixeldungeon.actors.buffs.Buff;
import com.knucklesgod.knucklesgodpixeldungeon.actors.hero.Hero;
import com.knucklesgod.knucklesgodpixeldungeon.effects.BlobEmitter;
import com.knucklesgod.knucklesgodpixeldungeon.effects.Identification;
import com.knucklesgod.knucklesgodpixeldungeon.effects.Speck;
import com.knucklesgod.knucklesgodpixeldungeon.items.Item;
import com.knucklesgod.knucklesgodpixeldungeon.journal.Notes.Landmark;
import com.knucklesgod.knucklesgodpixeldungeon.levels.Terrain;
import com.knucklesgod.knucklesgodpixeldungeon.messages.Messages;
import com.knucklesgod.knucklesgodpixeldungeon.scenes.GameScene;
import com.knucklesgod.knucklesgodpixeldungeon.tiles.DungeonTilemap;
import com.knucklesgod.knucklesgodpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class WaterOfAwareness extends WellWater {

	@Override
	protected boolean affectHero( Hero hero ) {
		
		Sample.INSTANCE.play( Assets.SND_DRINK );
		emitter.parent.add( new Identification( hero.sprite.center() ) );
		
		hero.belongings.observe();
		
		for (int i=0; i < Dungeon.level.length(); i++) {
			
			int terr = Dungeon.level.map[i];
			if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
				
				Dungeon.level.discover( i );
				
				if (Dungeon.level.heroFOV[i]) {
					GameScene.discoverTile( i, terr );
				}
			}
		}
		
		Buff.affect( hero, Awareness.class, Awareness.DURATION );
		Dungeon.observe();

		Dungeon.hero.interrupt();
	
		GLog.p( Messages.get(this, "procced") );
		
		return true;
	}
	
	@Override
	protected Item affectItem( Item item ) {
		if (item.isIdentified()) {
			return null;
		} else {
			item.identify();
			Badges.validateItemLevelAquired( item );
			
			emitter.parent.add( new Identification( DungeonTilemap.tileCenterToWorld( pos ) ) );
			
			return item;
		}
	}
	
	@Override
	protected Landmark record() {
		return Landmark.WELL_OF_AWARENESS;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.pour( Speck.factory( Speck.QUESTION ), 0.3f );
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
