package boostcourse.backend.cardmanager;

import boostcourse.backend.cardmanager.dao.BusinessCardManagerDao;
import boostcourse.backend.cardmanager.dto.BusinessCard;
import boostcourse.backend.cardmanager.ui.CardManagerUI;

import java.util.List;

public class CardManager {
    public static void main(String[] args) {
        CardManagerUI cardManagerUI = CardManagerUI.getInstance();
        BusinessCardManagerDao businessCardManagerDao = new BusinessCardManagerDao();

        while_loop:
        while (true) {
            cardManagerUI.printMainMenu();
            int menuNumber = cardManagerUI.getMenuNumber();
            switch (menuNumber) {
                case 1:
                    BusinessCard businessCard = cardManagerUI.inputBusinessCard();
                    businessCardManagerDao.addBusinessCard(businessCard);
                    break;
                case 2:
                    String searchKeyword = cardManagerUI.getSearchKeyword();
                    List<BusinessCard> businessCardList = businessCardManagerDao.searchBusinessCard(searchKeyword);
                    cardManagerUI.printBusinessCards(businessCardList);
                    break;
                case 3:
                    cardManagerUI.printExitMessage();
                    break while_loop;
                default:
                    cardManagerUI.printErrorMessage();
                    break;
            }
        }
    }
}
