# BattleMage Optimization

This repository explores decision-making in a programmable combat simulation (“BattleMage”) that I originally designed as a teaching system for AP Computer Science.

The system is **not a human-playable game**. There is no UI and no interactive play. Instead, agents are implemented as Java classes that choose actions based on the current opponent and threat state. Performance is evaluated by running many simulated battles and analyzing the resulting data.

What began as a pedagogical environment became a useful testbed for asking deeper questions about **variance, strategy evaluation, and how conclusions change as more data is considered**.

**Most readers should start with the Python analysis notebook; running the Java code is optional.**

---

## What This Project Is (and Is Not)

**This project is:**
- A programmable decision environment
- A simulation that produces data via repeated trials
- An exploration of how to reason about performance under uncertainty

**This project is not:**
- A game meant to be played by a human
- An interactive application
- A trained machine learning model

All “players” act through code.

---

## Core Question

> Given this ruleset, which decision strategies tend to perform best when evaluated over many simulated battles?

Related questions include:
- How noisy are observed outcomes?
- How rare are genuinely strong strategies?
- How does confidence change as more data is collected?

---

## Repository Structure
```
src/
├── actions/        # Available actions and mechanics
├── characters/     # Decision-making agents ("champions")
├── game/           # Core simulation logic
└── optimization/   # Multi-phase optimization drivers
```

- The **Java code** defines the system and generates data.
- The **Python notebook** analyzes that data phase by phase.

---

## How This Project Is Intended to Be Used

### 1. Run the Analysis Notebook (Recommended)

The simplest way to engage with the project is to run:
```
01_Optimizing_battleMage.ipynb
```


The notebook:
- Loads previously generated simulation results
- Walks through the analysis in phases
- Explains what each phase is testing and why

**No Java code needs to be run for this path.**

---

### 2. Inspect or Extend the Simulation (Optional)

Readers interested in system design can inspect the Java source to see:
- How decisions are encoded
- How threats and resources evolve
- How outcomes are scored

Advanced users can write new character classes, regenerate data, and reuse the notebook for analysis.

---

## Representative Results

To keep the project approachable, the README highlights **three plots** that capture the main ideas without reproducing the entire notebook.

---

### 1. Phase 1: Outcome Distribution Across the Search Space

![Phase 1 distribution of average final level](images/phase1_distribution.png)

**Caption:**  
*Distribution of average final level across all parameter sets explored in Phase 1. Most configurations perform poorly; high-performing outcomes lie in a narrow tail, motivating refinement rather than single-run evaluation.*

This plot establishes the central challenge: outcomes are highly variable, and genuinely strong strategies are rare.

---

### 2. Optimization Trajectory Across Phases

![Optimization trajectory narrowing uncertainty](images/optimization_trajectory.png)

**Caption:**  
*Average performance of the best-performing strategy across optimization phases. Error bars reflect uncertainty. Later phases reduce variance more than they increase mean performance, indicating increased confidence rather than simple overfitting.*

This plot shows how the optimization process evolves: not by chasing ever-higher scores, but by narrowing uncertainty around consistent performance.

---

### 3. Phase 2 Refined: What the Optimizer Learns

![Phase 2 refined parameter distributions](images/phase2_refined_parameters.png)

**Caption:**  
*Parameter distributions for all runs compared to the top 0.010% of performers after Phase 2 refinement. High-performing strategies occupy structured subregions of the original search space.*

Rather than producing a single “best” configuration, the analysis reveals which parameter ranges consistently support strong performance.

---

## Why This Structure Works

The BattleMage system was originally built for instruction, which led to:
- Explicit state representation
- Modular, readable code
- Clear separation between mechanics and analysis

Those same choices make it possible to treat the simulation as a data-generating process and analyze it independently.

This project reflects how I approach systems: start simple, test assumptions, and refine only when the data justifies it.

---

## Limitations

- Results are specific to the chosen opponent model and ruleset  
- Optimization focuses on average performance, not worst-case guarantees  
- The search explores a predefined parameterization of strategies  
- No attempt is made to learn or adapt during a battle  

These limitations are intentional: the goal is to understand **evaluation and uncertainty**, not to build a universally optimal agent.

---

## Closing Note

Although the setting is a combat simulation, the underlying questions—about noise, rare outcomes, and confidence under repeated trials—are the same ones that appear in many applied data problems.

This repository documents how my analysis evolved as those questions were tested against data.
