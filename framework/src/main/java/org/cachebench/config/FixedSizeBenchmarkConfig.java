package org.cachebench.config;

import org.cachebench.DistStage;
import org.cachebench.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A fixed size benchmark is a benchmark that executes over a fixed number of slaves. This defines the configuration of
 * such a benchmark.
 *
 * @see org.cachebench.config.ScalingBenchmarkConfig
 * @author Mircea.Markus@jboss.com
 */
public class FixedSizeBenchmarkConfig {

   private List<Stage> stages = new ArrayList<Stage>();

   private String productName;
   private String configName;
   private int size;


   public void setStages(List<Stage> stages) {
      this.stages = stages;
   }

   public List<Stage> getStages() {
      return new ArrayList<Stage>(stages);
   }

   public String getProductName() {
      return productName;
   }

   public void setProductName(String productName) {
      assertNo_(productName);
      this.productName = productName;
   }

   private void assertNo_(String name) {
      if (name.indexOf("_") >=0) {
         throw new RuntimeException("'_' not allowed in productName (reporting relies on that)");
      }
   }

   public String getConfigName() {
      return configName;
   }

   public void setConfigName(String configName) {
      int index = configName.indexOf('.');
      if (index > 0) {
         configName = configName.substring(0, index);
         index = configName.indexOf(File.separator);
         if (index > 0) {
            configName = configName.substring(configName.lastIndexOf(File.separator) + 1);
         }
      }
      assertNo_(configName);
      this.configName = configName;
   }

   public void validate() {
      if (productName == null) throw new RuntimeException("Name must be set!");
   }

   public List<Stage> getAssmbledStages() {
      for (Stage st: stages) {
         if (st instanceof DistStage) {
            ((DistStage)st).setActiveSlavesCount(size);
         }
      }
      return stages;
   }

   public void setSize(int size) {
      this.size = size;
   }
}
