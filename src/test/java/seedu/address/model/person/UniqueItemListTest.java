package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.UniqueItemList;
import seedu.address.model.person.exceptions.DuplicateItemException;
import seedu.address.model.person.exceptions.ItemNotFoundException;
import seedu.address.model.person.member.Member;
import seedu.address.testutil.MemberBuilder;

public class UniqueItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueItemList<Member> uniqueItemList = new UniqueItemList<>();

    @Test
    public void contains_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.contains(null);
    }

    @Test
    public void contains_memberNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(ALICE));
    }

    @Test
    public void contains_memberInList_returnsTrue() {
        uniqueItemList.add(ALICE);
        assertTrue(uniqueItemList.contains(ALICE));
    }

    @Test
    public void contains_memberWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(ALICE);
        Member editedAlice = new MemberBuilder(ALICE).build();
        assertTrue(uniqueItemList.contains(editedAlice));
    }

    @Test
    public void add_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.add(null);
    }

    @Test
    public void add_duplicateMember_throwsDuplicateMemberException() {
        uniqueItemList.add(ALICE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.add(ALICE);
    }

    @Test
    public void setMember_nullTargetMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(null, ALICE);
    }

    @Test
    public void setMember_nullEditedMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(ALICE, null);
    }

    @Test
    public void setMember_targetMemberNotInList_throwsMemberNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.setItem(ALICE, ALICE);
    }

    @Test
    public void setMember_editedMemberIsSameMember_success() {
        uniqueItemList.add(ALICE);
        uniqueItemList.setItem(ALICE, ALICE);
        UniqueItemList<Member> expectedUniqueItemList = new UniqueItemList<>();
        expectedUniqueItemList.add(ALICE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setMember_editedMemberHasSameIdentity_success() {
        uniqueItemList.add(ALICE);
        Member editedAlice = new MemberBuilder(ALICE).build();
        uniqueItemList.setItem(ALICE, editedAlice);
        UniqueItemList<Member> expectedUniqueItemList = new UniqueItemList<>();
        expectedUniqueItemList.add(editedAlice);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setMember_editedMemberHasDifferentIdentity_success() {
        uniqueItemList.add(ALICE);
        uniqueItemList.setItem(ALICE, BOB);
        UniqueItemList<Member> expectedUniqueItemList = new UniqueItemList<>();
        expectedUniqueItemList.add(BOB);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setMember_editedMemberHasNonUniqueIdentity_throwsDuplicateMemberException() {
        uniqueItemList.add(ALICE);
        uniqueItemList.add(BOB);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItem(ALICE, BOB);
    }

    @Test
    public void remove_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.remove(null);
    }

    @Test
    public void remove_memberDoesNotExist_throwsMemberNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.remove(ALICE);
    }

    @Test
    public void remove_existingMember_removesMember() {
        uniqueItemList.add(ALICE);
        uniqueItemList.remove(ALICE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setMembers_nullUniqueMemberList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((UniqueItemList<Member>) null);
        uniqueItemList.setItems((List<Member>) null);
    }

    @Test
    public void setMembers_uniqueMemberList_replacesOwnListWithProvidedUniqueMemberList() {
        uniqueItemList.add(ALICE);
        UniqueItemList<Member> expectedUniqueItemList = new UniqueItemList<>();
        expectedUniqueItemList.add(BOB);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setMembers_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((List<Member>) null);
    }

    @Test
    public void setMembers_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(ALICE);
        List<Member> memberList = Collections.singletonList(BOB);
        uniqueItemList.setItems(memberList);
        UniqueItemList<Member> expectedUniqueItemList = new UniqueItemList<>();
        expectedUniqueItemList.add(BOB);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setMembers_listWithDuplicateMembers_throwsDuplicateMemberException() {
        List<Member> listWithDuplicateMembers = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItems(listWithDuplicateMembers);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueItemList.asUnmodifiableObservableList().remove(0);
    }
}
