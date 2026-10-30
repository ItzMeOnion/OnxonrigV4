package com.onxon.marlows;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.ArrayList;
import java.util.List;

public class MarlowsMenuScreen extends Screen {
    private String page = "home";
    private final List<TextFieldWidget> fields = new ArrayList<>();
    private final List<String> keys = new ArrayList<>();
    private final List<String> labels = new ArrayList<>();
    public MarlowsMenuScreen() { super(Text.literal("Onxon's rig mod")); MarlowsConfig.load(); }

    @Override protected void init() {
        clearChildren(); fields.clear(); keys.clear(); labels.clear();
        if (page.equals("home")) {
            addPage("Paper", "paper", 80); addPage("BJ", "bj", 110);
            addPage("50/50", "fifty", 140); addPage("Odd or Even", "odd", 170);
            addPage("RR", "rr", 200);
            return;
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("< Back"), b -> open("home"))
            .dimensions(20, 20, 75, 20).build());
        int y=85;
        if (page.equals("paper")) {
            label("Paper: assign an item ID to each number 1-9");
            for(int i=1;i<=9;i++) addField("paper."+i, "Number "+i, y+(i-1)*25);
        } else if (page.equals("bj")) {
            label("BJ: assign an item/map ID to each value");
            for(String s:new String[]{"1_11","3","4","5","6","7","8","9","10"})
                addField("bj."+s, s.replace('_','/'), y+(fields.size())*25);
        } else if (page.equals("fifty")) {
            label("50/50: set the two item IDs");
            addField("fifty.left","Left item",y); addField("fifty.right","Right item",y+35);
        } else if (page.equals("odd")) {
            label("Odd and Even: assign four item IDs each");
            for(int i=1;i<=4;i++) addField("odd."+i,"Odd "+i,y+(i-1)*25);
            for(int i=1;i<=4;i++) addField("even."+i,"Even "+i,y+115+(i-1)*25);
            addField("or.item","Or item",y+225);
        } else label("RR configuration is reserved for a future update.");
        addDrawableChild(ButtonWidget.builder(Text.literal("Save settings"), b -> save())
            .dimensions(width-145,height-35,125,20).build());
    }
    private void addPage(String name,String id,int y) {
        addDrawableChild(ButtonWidget.builder(Text.literal(name), b -> open(id))
            .dimensions(width/2-105,y,210,23).build());
    }
    private void label(String s) { labels.add(s); }
    private void addField(String key,String name,int y) {
        TextFieldWidget f=new TextFieldWidget(textRenderer,55,y,300,20,Text.literal(name));
        f.setMaxLength(128); f.setPlaceholder(Text.literal("e.g. minecraft:diamond"));
        f.setText(MarlowsConfig.get(key)); addDrawableChild(f); fields.add(f); keys.add(key);
    }
    private void save() {
        for(int i=0;i<fields.size();i++) MarlowsConfig.put(keys.get(i),fields.get(i).getText().trim());
        MarlowsConfig.save();
    }
    private void open(String p) { page=p; init(); }
    @Override public void render(DrawContext c,int mx,int my,float d) {
        renderBackground(c,mx,my,d); c.fill(0,0,width,44,0xE5101422);
        c.drawCenteredTextWithShadow(textRenderer,"ONXON'S RIG MOD",width/2,13,0xFFB77CFF);
        c.drawCenteredTextWithShadow(textRenderer,page.equals("home")?"Marlow's crystal optimiser":page.toUpperCase(),width/2,53,0xFFFFFFFF);
        int y=70; for(String s:labels) { c.drawTextWithShadow(textRenderer,s,55,y,0xFFE0E3EC); y+=17; }
        super.render(c,mx,my,d);
    }
    @Override public boolean shouldPause() { return false; }
}
