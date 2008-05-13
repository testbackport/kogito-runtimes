package org.drools.reteoo;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.drools.common.InternalFactHandle;
import org.drools.util.Entry;
import org.drools.util.RightTupleList;

public class RightTuple
    implements
    Entry {
    private InternalFactHandle handle;

    private RightTuple         handlePrevious;
    private RightTuple         handleNext;

    private RightTupleList     memory;

    private Entry              previous;
    private Entry              next;

    private LeftTuple          betaChildren;

    private LeftTuple          blocked;

    private RightTupleSink     sink;

    private int                hashCode;

    public RightTuple() {

    }

    public RightTuple(InternalFactHandle handle,
                      RightTupleSink sink) {
        this.handle = handle;
        this.hashCode = this.handle.hashCode();
        this.sink = sink;

        RightTuple currentFirst = handle.getRightTuple();
        if ( currentFirst != null ) {
            currentFirst.handlePrevious = this;
            this.handleNext = currentFirst;
        }

        handle.setRightTuple( this );
    }

    public RightTupleSink getRightTupleSink() {
        return this.sink;
    }

    public void unlinkFromRightParent() {
        if ( this.handle != null ) {
            if( this.handlePrevious != null ) {
                this.handlePrevious.handleNext = this.handleNext;
            }
            if( this.handleNext != null ) {
                this.handleNext.handlePrevious = this.handlePrevious;
            }
            if( this.handle.getRightTuple() == this ) {
                this.handle.setRightTuple( this.handleNext );
            }
        }
        this.handle = null;
        this.handlePrevious = null;
        this.handleNext = null;
        this.blocked = null;
        this.previous = null;
        this.next = null;
        this.memory = null;
        this.betaChildren = null;
        this.sink = null;
    }

    public InternalFactHandle getFactHandle() {
        return this.handle;
    }

    public LeftTuple getBlocked() {
        return blocked;
    }

    public void setBlocked(LeftTuple blocked) {
        this.blocked = blocked;
    }

    public RightTupleList getMemory() {
        return memory;
    }

    public void setMemory(RightTupleList memory) {
        this.memory = memory;
    }

    public Entry getPrevious() {
        return previous;
    }

    public void setPrevious(Entry previous) {
        this.previous = previous;
    }

    public RightTuple getHandlePrevious() {
        return handlePrevious;
    }

    public void setHandlePrevious(RightTuple handlePrevious) {
        this.handlePrevious = handlePrevious;
    }

    public RightTuple getHandleNext() {
        return handleNext;
    }

    public void setHandleNext(RightTuple handleNext) {
        this.handleNext = handleNext;
    }

    public Entry getNext() {
        return next;
    }

    public void setNext(Entry next) {
        this.next = next;
    }

    public LeftTuple getBetaChildren() {
        return betaChildren;
    }

    public void setBetaChildren(LeftTuple betachildren) {
        this.betaChildren = betachildren;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public String toString() {
        return this.handle.toString() + "\n";
    }

    public boolean equals(RightTuple other) {
        // we know the object is never null and always of the  type ReteTuple
        if ( other == this ) {
            return true;
        }

        // A ReteTuple is  only the same if it has the same hashCode, factId and parent
        if ( (other == null) || (this.hashCode != other.hashCode) ) {
            return false;
        }

        return this.handle == other.handle;
    }

    public boolean equals(Object object) {
        return equals( (RightTuple) object );
    }

    public void readExternal(ObjectInput in) throws IOException,
                                            ClassNotFoundException {
        this.handle = (InternalFactHandle) in.readObject();
        this.handlePrevious = (RightTuple) in.readObject();
        this.handleNext = (RightTuple) in.readObject();
        this.memory = (RightTupleList) in.readObject();
        this.previous = (RightTuple) in.readObject();
        this.next = (RightTuple) in.readObject();
        this.betaChildren = (LeftTuple) in.readObject();
        this.blocked = (LeftTuple) in.readObject();
        this.sink = (RightTupleSink) in.readObject();
        this.hashCode = in.readInt();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject( this.handle );
        out.writeObject( this.handlePrevious );
        out.writeObject( this.handleNext );
        out.writeObject( this.memory );
        out.writeObject( this.previous );
        out.writeObject( this.next );
        out.writeObject( this.betaChildren );
        out.writeObject( this.blocked );
        out.writeObject( this.sink );
        out.writeInt( this.hashCode );
    }

}
