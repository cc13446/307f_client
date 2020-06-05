import Domain.Room;
import MyGui.GuiModel;
import MyGui.InitGuiModel;
import Enum.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Room room = new Room();
        GuiModel.initGlobalFontSetting(new Font("微软雅黑",Font.BOLD, 18));

        InitGuiModel.initGlobalFontSetting(new Font("微软雅黑",Font.BOLD, 18));
        InitGuiModel initGuiModel = new InitGuiModel(room);
        initGuiModel.setVisible(true);
    }
}
