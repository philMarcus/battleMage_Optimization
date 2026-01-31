# battleMage Optimization Dataset

This directory is the expected location for the full dataset used in the battleMage optimization analysis.

The dataset is not stored in the repository. All data is precomputed and released separately as a static archive via GitHub Releases. The notebook does not generate simulation data; it only analyzes the results contained in the released files.

If the required CSV files are not present, download the dataset from the project’s GitHub Releases page and extract it into this directory so that the expected file paths are satisfied.

---

## Downloading the data

Download the dataset from:

**GitHub → Releases → `data-v1.0.0`**

After extraction, place all CSV files directly into this `data/` directory.

Expected structure:

```
images/
data/
├── phase_zero_actions.csv
├── phase_zero_outcomes.csv
├── phase_one_outcomes.csv
├── phase_one_refined_outcomes.csv
├── phase_two_outcomes.csv
├── phase_two_refined_outcomes.csv
├── noise_calibration.csv
└── README.md
```
---

## Dataset structure and intent

The dataset is organized into three conceptual phases.

### Phase 0 — Input normalization and noise calibration

Phase 0 establishes the numerical baseline used throughout the project.

- **`phase_zero_actions.csv`**  
  Action vectors sampled to characterize the scale and distribution of input parameters.  
  Used for feature scaling and normalization.

- **`phase_zero_outcomes.csv`**  
  Corresponding outcome measurements used to estimate baseline output behavior.

- **`noise_calibration.csv`**  
  Repeated or controlled runs used to quantify stochastic variance in the simulator.  
  This data defines the noise floor against which all later improvements are evaluated.

Phase 0 is not an optimization phase. Its purpose is to define a stable coordinate system and uncertainty model for subsequent analysis.

---

### Phase 1 — Coarse optimization (Exploration)

- **`phase_one_outcomes.csv`**  
 Results from the initial exploratory optimization pass using normalized inputs and a broad search space.

- **`phase_one_refined_outcomes.csv`**  
 Results from a subsequent optimization pass using a re-parameterized search space derived from Phase 1 outcomes.
This phase is not a subset of Phase 1 results; it represents a new exploration constrained by the structure revealed in the initial pass.

---

### Phase 2 — Final optimization (Exploitation)

- **`phase_two_outcomes.csv`**  
  Optimization results produced by transitioning from exploratory search to exploitation, using the parameter ranges established by Phase 1R.

- **`phase_two_refined_outcomes.csv`**  
 Final exploitation pass using a tightly constrained search space derived from Phase 2 outcomes, intended for final evaluation and visualization.

---

## Regenerating the dataset

Regenerating the dataset requires running a multi-stage Java-based simulation and optimization pipeline with specific configuration and random seeds.

This process is intentionally **out of scope** for this repository.

This repository exists to analyze and interpret a fixed dataset, not to reproduce the underlying simulator.

---

## Scope and finality

This dataset represents the **final and complete** data release for the project.

- No additional datasets are planned
- Results and figures in the notebook are derived exclusively from these files
- Partial or downsampled versions are not provided

The analysis should be interpreted in the context of the full dataset.

---

## Versioning

The dataset is released as: `data-v1.0.0`

