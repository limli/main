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

public class UniqueMemberListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueItemList<Member> uniqueMemberList = new UniqueItemList<>();

    @Test
    public void contains_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMemberList.contains(null);
    }

    @Test
    public void contains_memberNotInList_returnsFalse() {
        assertFalse(uniqueMemberList.contains(ALICE));
    }

    @Test
    public void contains_memberInList_returnsTrue() {
        uniqueMemberList.add(ALICE);
        assertTrue(uniqueMemberList.contains(ALICE));
    }

    @Test
    public void contains_memberWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMemberList.add(ALICE);
        Member editedAlice = new MemberBuilder(ALICE).build();
        assertTrue(uniqueMemberList.contains(editedAlice));
    }

    @Test
    public void add_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMemberList.add(null);
    }

    @Test
    public void add_duplicateMember_throwsDuplicateMemberException() {
        uniqueMemberList.add(ALICE);
        thrown.expect(DuplicateItemException.class);
        uniqueMemberList.add(ALICE);
    }

    @Test
    public void setMember_nullTargetMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMemberList.setItem(null, ALICE);
    }

    @Test
    public void setMember_nullEditedMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMemberList.setItem(ALICE, null);
    }

    @Test
    public void setMember_targetMemberNotInList_throwsMemberNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueMemberList.setItem(ALICE, ALICE);
    }

    @Test
    public void setMember_editedMemberIsSameMember_success() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.setItem(ALICE, ALICE);
        UniqueItemList<Member> expectedUniqueMemberList = new UniqueItemList<>();
        expectedUniqueMemberList.add(ALICE);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_editedMemberHasSameIdentity_success() {
        uniqueMemberList.add(ALICE);
        Member editedAlice = new MemberBuilder(ALICE).build();
        uniqueMemberList.setItem(ALICE, editedAlice);
        UniqueItemList<Member> expectedUniqueMemberList = new UniqueItemList<>();
        expectedUniqueMemberList.add(editedAlice);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_editedMemberHasDifferentIdentity_success() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.setItem(ALICE, BOB);
        UniqueItemList<Member> expectedUniqueMemberList = new UniqueItemList<>();
        expectedUniqueMemberList.add(BOB);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_editedMemberHasNonUniqueIdentity_throwsDuplicateMemberException() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.add(BOB);
        thrown.expect(DuplicateItemException.class);
        uniqueMemberList.setItem(ALICE, BOB);
    }

    @Test
    public void remove_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMemberList.remove(null);
    }

    @Test
    public void remove_memberDoesNotExist_throwsMemberNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueMemberList.remove(ALICE);
    }

    @Test
    public void remove_existingMember_removesMember() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.remove(ALICE);
        UniqueItemList<Member> expectedUniqueMemberList = new UniqueItemList<>();
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_nullUniqueMemberList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMemberList.setItems((UniqueItemList<Member>) null);
    }

    @Test
    public void setMembers_uniqueMemberList_replacesOwnListWithProvidedUniqueMemberList() {
        uniqueMemberList.add(ALICE);
        UniqueItemList<Member> expectedUniqueMemberList = new UniqueItemList<>();
        expectedUniqueMemberList.add(BOB);
        uniqueMemberList.setItems(expectedUniqueMemberList);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMemberList.setItems((List<Member>) null);
    }

    @Test
    public void setMembers_list_replacesOwnListWithProvidedList() {
        uniqueMemberList.add(ALICE);
        List<Member> memberList = Collections.singletonList(BOB);
        uniqueMemberList.setItems(memberList);
        UniqueItemList<Member> expectedUniqueMemberList = new UniqueItemList<>();
        expectedUniqueMemberList.add(BOB);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_listWithDuplicateMembers_throwsDuplicateMemberException() {
        List<Member> listWithDuplicateMembers = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateItemException.class);
        uniqueMemberList.setItems(listWithDuplicateMembers);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMemberList.asUnmodifiableObservableList().remove(0);
    }
}
