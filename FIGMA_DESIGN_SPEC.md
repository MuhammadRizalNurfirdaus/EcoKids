# EcoKids - Spesifikasi Desain Figma
**Channel Figma:** uw3glg18

---

## 🎯 REFERENCE SCREENSHOT ANALYSIS

**Layout Type:** Badge/Logo Design
**Dimensions:** 800 x 800 px (recommended)
**Background:** White (#FFFFFF)

---

## 📐 STRUKTUR LAYOUT (Top to Bottom)

### **1. GAME CONTROLLER (Top Section)**
- **Position:** Top center, 80px from top
- **Width:** 180px
- **Height:** ~80px
- **Element:**
  - Controller body: Brown (#8B4513) with rounded edges
  - Left side: Yellow/gold circle (#FFD700)
  - Center: Blue circle (#4A90E2)
  - Right side: Green circle (#7ED321) + Red circle (#FF5252)
  - Buttons: Orange circles (#FF9800)
  - **Leaves decoration:**
    - Top left: Green leaf pointing left
    - Top right: Green leaf pointing right
    - Leaf color: #7ED321

---

### **2. ECOKIDS TEXT (Main Title)**
- **Position:** Below controller, centered
- **Spacing from controller:** 20px

#### "Eco" Text:
- **Font:** Comic/Playful rounded font (Cooper Black atau similar)
- **Size:** 120pt
- **Color:** Gradient Green
  - Top: #7ED321
  - Bottom: #4CAF50
- **Effect:** Slight 3D effect with darker green shadow
- **Outline:** Dark green stroke 4px (#2E7D32)

#### "Kids" Text:
- **Font:** Same as "Eco"
- **Size:** 120pt  
- **Color:** Gradient Blue
  - Top: #64B5F6
  - Bottom: #2196F3
- **Effect:** Slight 3D effect with darker blue shadow
- **Outline:** Dark blue stroke 4px (#1565C0)

---

### **3. CENTRAL ILLUSTRATION (Main Badge)**
- **Position:** Center of design
- **Width:** 600px
- **Height:** 500px
- **Background Shape:** Oval/ellipse with rounded edges
  - Fill: Sky blue gradient (#E3F2FD to #90CAF9)
  - Stroke: Dark blue outline 6px (#1976D2)

#### **Elements Inside Badge (Layered):**

**A. Background Elements:**
- **Sky:** Light blue (#E3F2FD) at top
- **Sun:** 
  - Position: Top left inside badge
  - Size: 80px diameter
  - Color: Yellow (#FDD835)
  - Rays: 8 short lines radiating outward

- **Clouds:**
  - Position: Top right
  - Color: White (#FFFFFF)
  - Count: 2 small fluffy clouds

- **Grass/Ground:**
  - Position: Bottom of oval
  - Color: Medium green (#66BB6A)
  - Style: Wavy top edge

**B. Main Character:**
- **Child (Boy):**
  - Position: Center-right
  - Hair: Orange/brown curly (#FF6F00)
  - Skin: Light peach (#FFCCBC)
  - Shirt: Orange (#FF9800)
  - Arms: Hugging the elephant
  - Expression: Happy, smiling

- **Elephant (Baby):**
  - Position: Center, being hugged by child
  - Color: Light blue-grey (#90A4AE)
  - Features:
    - Big ears (pink inside: #F8BBD0)
    - Rainbow mohawk: Red→Orange→Yellow→Green stripes on head
    - Trunk: Curved upward
    - Eyes: Large, cute, with white highlights
    - Blush: Pink cheeks (#F8BBD0)
    - Expression: Smiling, happy

**C. Surrounding Fruits (Arranged around characters):**

**Top Section (Left to Right):**
1. **Red Apple** (top left)
   - Asset: `img_apel.png`
   - Size: 100px x 100px
   - Position: Top left corner of badge

2. **Yellow Chicken** (not visible in main logo, but available)

**Bottom Left:**
3. **Banana**
   - Asset: `img_pisang.png`
   - Size: 100px x 100px
   - Position: Bottom left

**Bottom Section (Center-Bottom):**
4. **Purple Grapes**
   - Asset: `img_anggur.png`
   - Size: 100px x 100px
   - Position: Bottom center-left

**Bottom Right:**
5. **Strawberry**
   - Asset: `img_stroberi.png`
   - Size: 100px x 100px
   - Position: Bottom right

---

## 🎨 COLOR PALETTE

### Primary Colors:
- **Green (Nature):** #7ED321, #4CAF50, #66BB6A
- **Blue (Sky/Water):** #64B5F6, #2196F3, #90CAF9
- **Yellow (Sun/Energy):** #FDD835, #FFD700

### Secondary Colors:
- **Orange:** #FF9800, #FF6F00
- **Red:** #FF5252, #E53935
- **Pink:** #F8BBD0
- **Brown:** #8B4513, #6D4C41

### Neutral Colors:
- **White:** #FFFFFF
- **Light Blue Background:** #E3F2FD
- **Dark Outline:** #1565C0, #2E7D32

---

## 📝 TYPOGRAPHY

### Main Title (EcoKids):
- **Font Family:** Cooper Black / Fredoka One / Bubblegum Sans
- **Weight:** Bold/Black
- **Letter Spacing:** -2%
- **Alignment:** Center

### Alternative Fonts (if above not available):
1. Luckiest Guy
2. Baloo
3. Chewy

---

## 🖼️ ASSET PLACEMENT GUIDE

### Assets Already Available in Project:
```
/drawable/
├── logoecokids.png (Main reference)
├── img_alpukat.png
├── img_anggur.png (USE: Bottom center-left)
├── img_apel.png (USE: Top left)
├── img_ayam.png
├── img_burung.png
├── img_gajah.png (USE: Center main character)
├── img_jeruk.png
├── img_mangga.png
├── img_nanas.png
├── img_pepaya.png
├── img_pisang.png (USE: Bottom left)
├── img_semangka.png
├── img_stroberi.png (USE: Bottom right)
└── (other animal assets)
```

---

## 🔧 FIGMA SETUP INSTRUCTIONS

### Step 1: Create Main Frame
1. Create new frame: 800 x 800px
2. Name: "EcoKids Logo"
3. Background: White (#FFFFFF)

### Step 2: Build Game Controller (Top)
1. **Auto Layout - Horizontal**
2. Add rounded rectangle (180 x 80px)
   - Fill: #8B4513
   - Corner radius: 20px
3. Add circles for buttons
4. **Import leaf images** or create:
   - Left leaf: Rotate -30°
   - Right leaf: Rotate 30°

### Step 3: Add EcoKids Text
1. **Text Layer 1:** "Eco"
   - Type text
   - Apply green gradient fill
   - Add stroke: 4px #2E7D32
   - Add drop shadow: 0, 4, 8, #2E7D32 30%

2. **Text Layer 2:** "Kids"  
   - Type text
   - Apply blue gradient fill
   - Add stroke: 4px #1565C0
   - Add drop shadow: 0, 4, 8, #1565C0 30%

### Step 4: Create Main Badge
1. **Ellipse:** 600 x 500px
   - Fill: Linear gradient #E3F2FD → #90CAF9
   - Stroke: 6px #1976D2
   - Position: Center

2. **Inside badge, add:**
   - Sun (ellipse 80px, #FDD835) - top left
   - Clouds (white shapes) - top right
   - Grass (shape with wavy top) - bottom

### Step 5: Import Character Assets
1. **Drag & drop images:**
   - Elephant (center)
   - Child illustration (create with shapes or import)

2. **Arrange fruits:**
   - Drag `img_apel.png` → Top left
   - Drag `img_pisang.png` → Bottom left
   - Drag `img_anggur.png` → Bottom center-left
   - Drag `img_stroberi.png` → Bottom right

3. **Scale & position:**
   - Each fruit: ~100-120px
   - Use Auto Layout for equal spacing
   - Set image fill to "Fit" or "Fill"

### Step 6: Effects & Final Touches
1. Add subtle shadow to main badge:
   - 0, 8, 16, rgba(0,0,0,0.15)

2. Group all elements:
   - Select all → Cmd/Ctrl + G
   - Name: "EcoKids Complete Logo"

3. Export:
   - PNG @ 2x (1600 x 1600px)
   - PNG @ 1x (800 x 800px)
   - SVG (for scalability)

---

## ✅ CHECKLIST

Before finalizing, verify:
- [ ] Game controller has leaves decoration
- [ ] "Eco" text is green with gradient
- [ ] "Kids" text is blue with gradient
- [ ] Main badge is oval with blue gradient
- [ ] Sun in top left of badge
- [ ] Clouds in top right of badge
- [ ] Child hugging elephant in center
- [ ] Elephant has rainbow mohawk
- [ ] 4 fruits positioned correctly (apel, pisang, anggur, stroberi)
- [ ] All colors match the palette
- [ ] Spacing is balanced
- [ ] Export at correct sizes

---

## 📤 EXPORT SETTINGS

### For Digital Use:
- **Format:** PNG
- **Resolution:** 2x (1600 x 1600px)
- **Background:** Transparent or White

### For Print:
- **Format:** PDF or SVG
- **Resolution:** Vector (scalable)

### For Android App:
- **Export multiple sizes:**
  - xxxhdpi: 800 x 800px
  - xxhdpi: 600 x 600px
  - xhdpi: 400 x 400px
  - hdpi: 300 x 300px
  - mdpi: 200 x 200px

---

## 🎯 CHANNEL INFO
**Figma Channel:** `uw3glg18`
**Project Name:** EcoKids Educational App
**Design Type:** Logo/Badge Design

---

**Catatan:** Semua asset gambar sudah tersedia di folder `/app/src/main/res/drawable/`. Upload asset tersebut ke Figma untuk placement yang presisi.
