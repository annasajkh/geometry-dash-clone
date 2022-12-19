package com.github.annasajkh.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.utils.Global;

public abstract class Entity extends GameObject
{
    public Vector2 velocity;
    

    public Entity(Vector2 position, Vector2 velocity, float width, float height, float rotation, Color color)
    {
        super(position, width, height, rotation, color);
        
        this.velocity = velocity;

    }

    @Override
    public void update(float delta)
    {
        velocity.y -= Global.gravity;
        velocity.y = MathUtils.clamp(velocity.y, -Global.maxFallingSpeed, Global.maxFallingSpeed);
        
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

}
