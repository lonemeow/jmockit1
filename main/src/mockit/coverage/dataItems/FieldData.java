/*
 * Copyright (c) 2006 JMockit developers
 * This file is subject to the terms of the MIT license (see LICENSE.txt).
 */
package mockit.coverage.dataItems;

import java.io.*;
import javax.annotation.*;

public abstract class FieldData implements Serializable
{
   private static final long serialVersionUID = 8565599590976858508L;

   @Nonnegative int readCount;
   @Nonnegative int writeCount;
   @Nullable Boolean covered;

   private void writeObject(@Nonnull ObjectOutputStream out) throws IOException {
      isCovered();
      out.defaultWriteObject();
   }

   @Nonnegative public final int getReadCount()  { return readCount; }
   @Nonnegative public final int getWriteCount() { return writeCount; }

   public final boolean isCovered() {
      if (covered == null) {
         covered = false;
         markAsCoveredIfNoUnreadValuesAreLeft();
      }

      return covered;
   }

   abstract void markAsCoveredIfNoUnreadValuesAreLeft();

   final void addCountsFromPreviousTestRun(@Nonnull FieldData previousInfo) {
      readCount += previousInfo.readCount;
      writeCount += previousInfo.writeCount;
      covered = isCovered() || previousInfo.isCovered();
   }
}