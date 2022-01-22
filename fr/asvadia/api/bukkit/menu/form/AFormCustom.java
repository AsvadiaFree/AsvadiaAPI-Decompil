package fr.asvadia.api.bukkit.menu.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.CustomForm;
import org.geysermc.cumulus.Form;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.impl.Component;
import org.geysermc.cumulus.component.impl.DropdownComponentImpl;
import org.geysermc.cumulus.component.impl.InputComponentImpl;
import org.geysermc.cumulus.component.impl.LabelComponentImpl;
import org.geysermc.cumulus.component.impl.SliderComponentImpl;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;
import org.geysermc.cumulus.component.impl.ToggleComponentImpl;
import org.geysermc.cumulus.impl.CustomFormImpl;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.impl.FormImageImpl;

public class AFormCustom implements AForm {
  CustomForm form;
  
  BiConsumer<Player, CustomFormResponse> response;
  
  public AFormCustom(CustomForm form, BiConsumer<Player, CustomFormResponse> response) {
    this.form = form;
    this.response = response;
  }
  
  public CustomForm getForm() {
    return this.form;
  }
  
  public BiConsumer<Player, CustomFormResponse> getResponse() {
    return this.response;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public static class Builder implements AForm.Builder {
    String title = "";
    
    FormImage icon;
    
    BiConsumer<Player, CustomFormResponse> response;
    
    List<Component> components = new ArrayList<>();
    
    public Builder title(String title) {
      if (title != null)
        this.title = title; 
      return this;
    }
    
    public Builder icon(FormImage.Type type, String data) {
      return icon((FormImage)new FormImageImpl(type, data));
    }
    
    public Builder icon(FormImage formImage) {
      this.icon = formImage;
      return this;
    }
    
    public Builder response(BiConsumer<Player, CustomFormResponse> response) {
      this.response = response;
      return this;
    }
    
    public Builder label(String text) {
      return component((Component)new LabelComponentImpl(text));
    }
    
    public Builder input(String text) {
      return input(text, "");
    }
    
    public Builder input(String text, String defaultText) {
      return component((Component)new InputComponentImpl(text, defaultText, ""));
    }
    
    public Builder dropDown(String text, int defaultOption, String... options) {
      return dropDown(text, defaultOption, Arrays.asList(options));
    }
    
    public Builder dropDown(String text, int defaultOption, List<String> options) {
      return component((Component)new DropdownComponentImpl(text, options, defaultOption));
    }
    
    public Builder stepSlider(String text, int defaultStep, String... steps) {
      return stepSlider(text, defaultStep, Arrays.asList(steps));
    }
    
    public Builder stepSlider(String text, int defaultStep, List<String> steps) {
      return component((Component)new StepSliderComponentImpl(text, steps, defaultStep));
    }
    
    public Builder slider(String text, int min, int max) {
      return slider(text, min, max, 1);
    }
    
    public Builder slider(String text, int min, int max, int step) {
      return slider(text, min, max, step, min);
    }
    
    public Builder slider(String text, int min, int max, int step, int defaultValue) {
      return component((Component)new SliderComponentImpl(text, min, max, step, defaultValue));
    }
    
    public Builder toggle(String text) {
      return toggle(text, false);
    }
    
    public Builder toggle(String text, boolean defaultValue) {
      return component((Component)new ToggleComponentImpl(text, defaultValue));
    }
    
    public Builder component(Component component) {
      if (component != null) {
        try {
          Field field = Component.class.getDeclaredField("text");
          field.setAccessible(true);
          field.set(component, ChatColor.translateAlternateColorCodes('&', component.getText()));
        } catch (NoSuchFieldException|IllegalAccessException e) {
          e.printStackTrace();
        } 
        this.components.add(component);
      } 
      return this;
    }
    
    public AFormCustom build(Player player) {
      if (Bukkit.getPluginManager().getPlugin("PlaceHolderAPI") != null)
        try {
          Field field = Component.class.getDeclaredField("text");
          field.setAccessible(true);
          for (Component component : this.components)
            field.set(component, PlaceholderAPI.setPlaceholders(player, component.getText())); 
        } catch (NoSuchFieldException|IllegalAccessException e) {
          e.printStackTrace();
        }  
      return build();
    }
    
    public AFormCustom build() {
      return new AFormCustom((CustomForm)new CustomFormImpl(this.title, this.icon, this.components), this.response);
    }
  }
}
