package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMembers.getTypicalMembers;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMember;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.MemberCardHandle;
import guitests.guihandles.MemberListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Email;
import seedu.address.model.person.LoyaltyPoints;
import seedu.address.model.person.Member;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class MemberListPanelTest extends GuiUnitTest {
    private static final ObservableList<Member> TYPICAL_MEMBERS =
            FXCollections.observableList(getTypicalMembers());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Member> selectedMember = new SimpleObjectProperty<>();
    private MemberListPanelHandle memberListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_MEMBERS);

        for (int i = 0; i < TYPICAL_MEMBERS.size(); i++) {
            memberListPanelHandle.navigateToCard(TYPICAL_MEMBERS.get(i));
            Member expectedMember = TYPICAL_MEMBERS.get(i);
            MemberCardHandle actualCard = memberListPanelHandle.getMemberCardHandle(i);

            assertCardDisplaysMember(expectedMember, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedMemberChanged_selectionChanges() {
        initUi(TYPICAL_MEMBERS);
        Member secondMember = TYPICAL_MEMBERS.get(INDEX_SECOND.getZeroBased());
        guiRobot.interact(() -> selectedMember.set(secondMember));
        guiRobot.pauseForHuman();

        MemberCardHandle expectedMember = memberListPanelHandle.getMemberCardHandle(INDEX_SECOND.getZeroBased());
        MemberCardHandle selectedMember = memberListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedMember, selectedMember);
    }

    /**
     * Verifies that creating and deleting large number of members in {@code MemberListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Member> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of member cards exceeded time limit");
    }

    /**
     * Returns a list of members containing {@code memberCount} members that is used to populate the
     * {@code MemberListPanel}.
     */
    private ObservableList<Member> createBackingList(int memberCount) {
        ObservableList<Member> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < memberCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            LoyaltyPoints loyaltyPoints = new LoyaltyPoints(1);
            Member member = new Member(name, phone, email, loyaltyPoints);
            backingList.add(member);
        }
        return backingList;
    }

    /**
     * Initializes {@code memberListPanelHandle} with a {@code MemberListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code MemberListPanel}.
     */
    private void initUi(ObservableList<Member> backingList) {
        MemberListPanel memberListPanel =
                new MemberListPanel(backingList, selectedMember, selectedMember::set);
        uiPartRule.setUiPart(memberListPanel);

        memberListPanelHandle = new MemberListPanelHandle(getChildNode(memberListPanel.getRoot(),
                MemberListPanelHandle.MEMBER_LIST_VIEW_ID));
    }
}
