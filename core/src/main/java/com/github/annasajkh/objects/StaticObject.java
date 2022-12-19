package com.github.annasajkh.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.components.GameObject;

public abstract class StaticObject extends GameObject
{
    public enum StaticObjectType
    {
        STATIC_RECTANGLE,
        SPIKE
    }
    
    public StaticObjectType staticObjectType;
    
    public StaticObject(Vector2 position, StaticObjectType staticObjectType, float width, float height, float rotation, Color color)
    {
        super(position, width, height, rotation, color);
        
        this.staticObjectType = staticObjectType;
    }

}
