// Translated from Java Main.java to C++11
// Reads T test cases. Each test: n K then n integers.
// For each test, produce array after K bubble-like operations.
// Algorithm: sort elements by value (stable by original index). Maintain ordered set of free positions
// (0..n-1). For each element (in increasing value) place it at the earliest free position >= max(0, origIdx - K).
// Complexity: O(n log n) per test.
#pragma GCC optimize(3, "Ofast", "inline", "no-stack-protector", "unroll-loops")
#include <iostream>
#include <vector>
#include <set>
#include <algorithm>
#include <utility>

using namespace std;
static const auto faster_streams = [] {
    srand(time(nullptr));
    // use time to init the random seed
    std::ios::sync_with_stdio(false);
    std::istream::sync_with_stdio(false);
    std::ostream::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    // 关闭c++风格输入输出 , 与C风格输入输出的同步,提高性能.
    return 0;
}();

int main() {
    int T;
    if (!(cin >> T)) return 0;
    while (T--) {
        int n, K;
        cin >> n >> K;
        vector<int> a(n);
        for (int i = 0; i < n; ++i) cin >> a[i];

        // pairs: (value, original index)
        vector<pair<int,int>> pairs;
        pairs.reserve(n);
        for (int i = 0; i < n; ++i) pairs.emplace_back(a[i], i);
        stable_sort(pairs.begin(), pairs.end(), [](const pair<int,int>& p1, const pair<int,int>& p2){
            if (p1.first != p2.first) return p1.first < p2.first;
            return p1.second < p2.second;
        });

        set<int> free_pos;
        for (int i = 0; i < n; ++i) free_pos.insert(i);

        vector<int> res(n);
        for (const auto &p : pairs) {
            int val = p.first;
            int orig = p.second;
            int desired = orig - K;
            if (desired < 0) desired = 0;
            auto it = free_pos.lower_bound(desired);
            if (it == free_pos.end()) {
                // shouldn't really happen; fallback to last
                auto it2 = free_pos.end();
                --it2;
                it = it2;
            }
            int pos = *it;
            res[pos] = val;
            free_pos.erase(it);
        }

        // output
        for (int i = 0; i < n; ++i) {
            if (i) cout << ' ';
            cout << res[i];
        }
        cout << '\n';
    }
    return 0;
}
