package dev.jellyhand;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public final class JellyHandScreen extends Screen {
    private long openedAt;
    private final Settings s = JellyHandClient.SETTINGS;
    protected JellyHandScreen() { super(Text.literal("JellyHand")); }
    @Override protected void init() {
        openedAt = System.currentTimeMillis();
        int cx = width / 2, y = height / 2 - 65;
        addDrawableChild(ButtonWidget.builder(toggle("Jelly Animation", s.jellyAnimation), b -> { s.jellyAnimation=!s.jellyAnimation; b.setMessage(toggle("Jelly Animation", s.jellyAnimation)); s.save(); }).dimensions(cx-105,y,210,20).build());
        addDrawableChild(ButtonWidget.builder(toggle("Hand Position", s.handPosition), b -> { s.handPosition=!s.handPosition; b.setMessage(toggle("Hand Position", s.handPosition)); s.save(); }).dimensions(cx-105,y+27,210,20).build());
        addDrawableChild(new SliderWidget(cx-105,y+57,210,20,Text.literal("Animation speed"),s.animationSpeed) { @Override protected void updateMessage(){setMessage(Text.literal("Animation speed: "+Math.round(value*100)+"%"));} @Override protected void applyValue(){s.animationSpeed=value;s.save();} });
        addDrawableChild(new SliderWidget(cx-105,y+84,210,20,Text.literal("Hand X"), (s.handX+1)/2) { @Override protected void updateMessage(){setMessage(Text.literal("Hand X: "+String.format("%.2f",s.handX)));} @Override protected void applyValue(){s.handX=value*2-1;s.save();} });
        addDrawableChild(new SliderWidget(cx-105,y+111,210,20,Text.literal("Hand Y"), (s.handY+1)/2) { @Override protected void updateMessage(){setMessage(Text.literal("Hand Y: "+String.format("%.2f",s.handY)));} @Override protected void applyValue(){s.handY=value*2-1;s.save();} });
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close()).dimensions(cx-55,y+145,110,20).build());
    }
    private Text toggle(String name, boolean on) { return Text.literal(name+": "+(on?"ON":"OFF")); }
    @Override public void render(DrawContext c,int mouseX,int mouseY,float delta) {
        renderBackground(c,mouseX,mouseY,delta);
        float t=Math.min(1f,(System.currentTimeMillis()-openedAt)/220f); int alpha=(int)(t*255);
        c.fill(0,0,width,height,0x99060912);
        int left=width/2-125, top=height/2-100;
        c.fill(left,top,left+250,top+225,0xEE111827);
        c.fill(left,top,left+250,top+3,0xFF9B6DFF);
        c.drawCenteredTextWithShadow(textRenderer,"JELLYHAND",width/2,top+13,0xFFFFFFFF);
        c.drawCenteredTextWithShadow(textRenderer,"Client animations  •  1.21.4",width/2,top+27,0xFF9CA3AF);
        super.render(c,mouseX,mouseY,delta);
    }
    @Override public void close() { s.save(); if (client != null) client.setScreen(null); }
    @Override public boolean shouldPause(){return false;}
}
