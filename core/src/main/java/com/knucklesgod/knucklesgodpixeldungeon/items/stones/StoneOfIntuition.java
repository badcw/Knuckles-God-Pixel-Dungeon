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

package com.knucklesgod.knucklesgodpixeldungeon.items.stones;

import com.knucklesgod.knucklesgodpixeldungeon.items.Item;
import com.knucklesgod.knucklesgodpixeldungeon.sprites.ItemSpriteSheet;
import com.knucklesgod.knucklesgodpixeldungeon.windows.WndBag;

public class StoneOfIntuition extends InventoryStone {
	
	
	{
		mode = WndBag.Mode.UNIDED_POTION_OR_SCROLL;
		image = ItemSpriteSheet.STONE_ISAZ;
	}
	
	@Override
	protected void onItemSelected(Item item) {
		
		//TODO design and implement interface for this.
		//GameScene.show( new Window());
		
	}
	
	
}
