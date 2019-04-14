package seedu.address.model;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateItemException;
import seedu.address.model.person.member.Member;

/**
 * The API that stores the member side of the model.
 */
public interface MemberModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /**
     * Returns true if a member with the same identity as {@code member} exists in the restaurant book.
     */
    boolean hasMember(Member member);

    /**
     * Deletes the given member.
     * The member must exist in the restaurant book.
     */
    void deleteMember(Member target);

    /**
     * Adds the given member.
     * {@code member} must not already exist in the restaurant book.
     */
    void addMember(Member member);

    /**
     * Replaces the given member {@code target} with {@code editedMember}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedMember}
     * must not be the same as another existing member in the restaurant book.
     * @throws DuplicateItemException if editing will result in a duplicate member. In that case,
     * no members will be modified. Note that we cannot simply use
     * {@code !target.isSameItem(editedMember) && hasPerson(editedMember)} as that assumes transitivity
     * of the isSameItem operator.
     */
    void setMember(Member target, Member editedMember) throws DuplicateItemException;

    /** Returns an unmodifiable view of the filtered member list */
    ObservableList<Member> getFilteredMemberList();

    /**
     * Updates the filter of the filtered member list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemberList(Predicate<Member> predicate);

    /**
     * Selected member in the filtered member list.
     * null if no member is selected.
     */
    ReadOnlyProperty<Member> selectedMemberProperty();

    /**
     * Returns the selected member in the filtered member list.
     * null if no member is selected.
     */
    Member getSelectedMember();

    /**
     * Sets the selected member in the filtered member list.
     */
    void setSelectedMember(Member member);
}
