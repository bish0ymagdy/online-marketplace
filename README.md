# 🛒 Java EE Online Marketplace

A full-stack, dynamic Java EE web application designed as an open product marketplace catalog. The platform operates on a decentralized, user-driven model: any registered user can list products they want to sell, specify prices in EGP, add clear descriptions, and leave their contact information. Other users can then browse the public catalog, search for specific items, and get in touch with the seller directly to complete the purchase.

---

## 🚀 Key Application Features

### 1. Public Vendor Showcase & Contact Integration
The platform acts as a digital billboard for independent sellers:
* **Item Listing Creation:** Users can list a product name, price in EGP, and a descriptive breakdown of what they are selling.
* **Seller Contact Mapping:** Descriptions can include custom contact information (such as phone numbers or emails) so prospective buyers can reach out instantly off-platform.
* **Owner-Only Modifications:** The system checks the active session user ID against the product's vendor ID, displaying **Update** and **Delete** actions strictly to the person who created the listing.

### 2. Live Inventory Filtering
Using server-side filtering via the **Java Streams API**, the application separates and focuses item list views on the fly:
* **All Items View:** Renders the public marketplace directory populated by all active system users—perfect for buyers scanning the market.
* **My Uploads Only:** Instantly isolates and shows only the specific items added by the currently logged-in user, allowing sellers to easily manage their live listings.

### 3. Case-Insensitive Keyword Search
Built directly into the dashboard console, the search function looks through catalog data in real time:
* **Multi-Field Querying:** Evaluates search text against both the `Item Name` and `Item Description` fields simultaneously, making it easy to find products or specific seller tags.
* **Sanitized Query Matching:** Trims white spaces and forces matching fields into lowercase text streams to prevent missing any relevant results.

### 4. Advanced Security Gates & Session Routing
The application features a rock-solid, closed-loop state machine for web routing:
* **Session Cache Eviction:** Uses explicit HTTP `Cache-Control` header declarations to intercept the browser’s back-button history cache, stopping unauthorized page lookups.
* **Server-Side Session Verification:** Automatically forces an authenticated session check at the gateway layer. If an active user hits the landing URL, they skip the login form entirely and are sent straight to their working catalog dashboard.
* **Session Invalidation:** Completely wipes data models, destroys user context strings, and returns the client to an unauthenticated login state when the **Logout** button is triggered.

---

## 🛠 Structural System Architecture

The project is built on standard Enterprise Java architectures (MVC) to keep layers completely independent and maintainable:

* **Service Layer (M):** Handles core business logic, input validation, and Java Stream data filtering operations.
* **Presentation Layer (V):** Interactive, responsive Dark Theme interfaces styled with modern CSS layouts and structural JSP dynamic rendering scopes.
* **Controller Layer (C):** Java Servlets (`LoginServlet` and `ItemServlet`) managing multi-functional operational requests, state mutations, parameters parsing, and secure forwarding workflows.
* **Data Access Layer (DAO):** Abstract Data Access Objects (DAO Pattern) masking lower-level JDBC interactions, explicit database constraint validations, and connection pooling resources.
