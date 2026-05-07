# Oh My OpenCode 完整使用指南

> 多模型 Agent 编排系统，将单一 AI Agent 转变为协同开发团队

---

## 目录

- [一、快速开始](#一快速开始)
- [二、安装与配置](#二安装与配置)
- [三、Provider 自定义配置](#三provider-自定义配置)
- [四、Agent 系统](#四agent-系统)
- [五、计划系统详解](#五计划系统详解)
- [六、工作模式](#六工作模式)
- [七、文件引用与搜索](#七文件引用与搜索)
- [八、自定义命令](#八自定义命令)
- [九、模型切换](#九模型切换)
- [十、完整工作流示例](#十完整工作流示例)

---

## 一、快速开始

### 0. 前置准备：安装 Bun

**Bun** 是一个极速的 JavaScript 运行时、包管理器、测试运行器和打包器。我们推荐使用 Bun 来安装和管理 OpenCode 及 Oh My OpenCode。

#### 一键安装 Bun

**Linux / macOS / WSL:**
```bash
curl -fsSL https://bun.sh/install | bash
```

**Windows (PowerShell):**
```powershell
irm bun.sh/install.ps1 | iex
```

#### 验证安装

```bash
bun --version
```

安装成功后会显示版本号（如 1.3.9）。

#### 为什么使用 Bun？

| 特性 | Bun | npm | pnpm |
|------|-----|-----|------|
| 安装速度 | ⚡️ 极快 | 🐢 慢 | ⚡️ 快 |
| 磁盘占用 | 💾 小 | 💾 大 | 💾 中 |
| 兼容性 | ✅ 完全兼容 npm | - | ✅ 完全兼容 npm |
| 一致性 | ✅ 单一工具链 | ⚠️ 需要单独安装 npx | ✅ 统一 |

---

### 1.1 安装 OpenCode

```bash
# 推荐方式：安装脚本
curl -fsSL https://opencode.ai/install | bash

# 或使用 bun
bun install -g opencode-ai

# 或使用 Homebrew (macOS/Linux)
brew install opencode-ai/tap/opencode

# 或使用 Scoop (Windows)
scoop install opencode
```

验证安装：
```bash
opencode --version  # 需要 1.0.133 或更高版本
```

### 1.2 安装 Oh My OpenCode

```bash
# 推荐方式：使用 bunx
bunx oh-my-opencode install

# 或使用 bun
bun install -g oh-my-opencode

# 或使用 Homebrew
brew install oh-my-opencode

# 或使用 Scoop (Windows)
scoop install oh-my-opencode
```

### 1.3 配置 Zhipuai Coding Plan Provider

```bash
opencode auth login
```

在提示中选择：
```
┌  Add credential
│
◆  Select provider
│  ● Z.AI Coding Plan  ← 如果有订阅，选择这个
│  ● Z.AI              ← 标准 Z.AI API
└
```

获取 API Key：https://z.ai/manage-apikey/apikey-list

### 1.4 启动 OpenCode

```bash
cd your-project
opencode
```

### 1.5 第一个任务

安装完成后，只需输入：

```
ultrawork
```

就这么简单！Agent 会自动：
- 探索你的代码库
- 研究最佳实践
- 实现功能
- 验证结果
- 持续工作直到完成

想要更多控制？按 **Tab** 键进入 Prometheus 计划模式，然后运行 `/start-work` 进行完整编排。

---

## 二、安装与配置

### 2.1 配置文件位置

| 位置 | 优先级 | 用途 |
|------|--------|------|
| `~/.config/opencode/opencode.json` | 低 | 全局配置 |
| `{project}/opencode.json` | 高 | 项目配置（覆盖全局） |
| `~/.config/opencode/oh-my-opencode.json` | 低 | OMO 全局配置 |
| `{project}/.opencode/oh-my-opencode.json` | 高 | OMO 项目配置 |
| 环境变量 `OPENCODE_CONFIG` | 最高 | 自定义路径 |

### 2.2 配置文件优先级

项目级配置 > 全局配置

- **项目级配置**：针对特定项目的设置（如模型选择、Agent 配置）
- **全局配置**：通用设置（如主题、快捷键、Provider）

---

## 三、Provider 自定义配置

OpenCode 支持通过 JSON 配置文件自定义 Provider，**无需使用环境变量**，直接将 API Key 写在配置文件中。

### 3.1 配置 Zhipuai Coding Plan

`~/.config/opencode/opencode.json`：

```json
{
  "$schema": "https://opencode.ai/config.json",
  "model": "zhipuai/glm-4.7",
  "provider": {
    "zhipuai": {
      "npm": "@ai-sdk/openai-compatible",
      "name": "Zhipuai Coding Plan",
      "options": {
        "baseURL": "https://open.bigmodel.cn/api/paas/v4",
        "headers": {
          "Authorization": "Bearer YOUR_API_KEY_HERE"
        }
      },
      "models": {
        "glm-4.7": {
          "name": "GLM-4.7",
          "maxTokens": 128000
        }
      }
    }
  }
}
```

### 3.2 配置代理服务

```json
{
  "$schema": "https://opencode.ai/config.json",
  "provider": {
    "anthropic": {
      "options": {
        "baseURL": "https://your-proxy.com/v1",
        "headers": {
          "Authorization": "Bearer YOUR_API_KEY"
        }
      }
    }
  }
}
```

### 3.3 多 Provider 配置

```json
{
  "$schema": "https://opencode.ai/config.json",
  "model": "zhipuai/glm-4.7",
  "provider": {
    "zhipuai": {
      "npm": "@ai-sdk/openai-compatible",
      "name": "Zhipuai",
      "options": {
        "baseURL": "https://open.bigmodel.cn/api/paas/v4",
        "headers": {
          "Authorization": "Bearer YOUR_ZHIPUAI_KEY"
        }
      }
    },
    "anthropic": {
      "options": {
        "baseURL": "https://api.anthropic.com/v1",
        "headers": {
          "Authorization": "Bearer YOUR_ANTHROPIC_KEY"
        }
      }
    }
  }
}
```

### 3.4 Oh My OpenCode 项目级配置

`{project}/.opencode/oh-my-opencode.json`：

```json
{
  "agents": {
    "sisyphus": {
      "model": "zhipuai/glm-4.7"
    },
    "prometheus": {
      "model": "anthropic/claude-opus-4-6"
    },
    "metis": {
      "model": "anthropic/claude-opus-4-6"
    },
    "momus": {
      "model": "openai/gpt-4.1"
    },
    "atlas": {
      "model": "moonshot/kimi-k2"
    }
  }
}
```

---

## 四、系统架构总览

> 📖 **官方文档**: [Oh My OpenCode Features Reference](https://github.com/code-yeongyu/oh-my-opencode/blob/dev/docs/reference/features.md)

Oh My OpenCode 是一个**多 Agent 编排系统**，通过专业化分工和协作，将单一 AI Agent 转变为高效的开发团队。

### 4.1 核心设计理念

#### 单一 Agent 的局限性

传统单一 AI Agent 面临的挑战：
- **上下文过载**：所有任务都由一个 Agent 处理，难以深入专业领域
- **认知漂移**：在复杂任务中容易偏离目标
- **验证缺失**：缺乏质量控制和审查机制

#### Oh My OpenCode 的解决方案

```
专业化分工 + 并行执行 + 强制完成 = 高效交付
```

| 设计原则 | 实现方式 |
|----------|----------|
| **分离关注点** | 不同 Agent 专注于不同领域（计划、执行、审查、探索） |
| **并行执行** | 多个 Agent 同时工作，最大化吞吐量 |
| **强制完成** | 通过 Todo 驱动和循环机制确保任务完成 |
| **质量控制** | Metis 预审查 + Momus 后审查 |
| **模型优化** | 根据任务特点选择最优模型 |

### 4.2 系统层次架构

```
┌─────────────────────────────────────────────────────────────┐
│                      用户交互层                              │
│                    (Sisyphus - 主编排者)                       │
├─────────────────────────────────────────────────────────────┤
│                        规划层                                 │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐                    │
│  │ Metis    │→│ Prometheus│→│  Momus   │                    │
│  │预计划顾问│  │ 计划专家 │  │计划审查  │                    │
│  └──────────┘  └──────────┘  └──────────┘                    │
├─────────────────────────────────────────────────────────────┤
│                        编排层                                 │
│  ┌──────────┐  ┌──────────────┐                            │
│  │  Atlas   │  │Sisyphus-Junior│                            │
│  │执行协调器│  │Category 执行器 │                            │
│  └──────────┘  └──────────────┘                            │
├─────────────────────────────────────────────────────────────┤
│                        专业层                                 │
│  ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐ ┌──────────┐          │
│  │Oracle│ │Explore│ │Librar│ │Hephae│ │Multimodal│          │
│  │架构  │ │探索  │ │ian   │ │stus  │ │-Looker   │          │
│  │咨询  │ │代码  │ │文档  │ │深度  │ │视觉分析  │          │
│  └──────┘ └──────┘ └──────┘ └──────┘ └──────────┘          │
├─────────────────────────────────────────────────────────────┤
│                      基础设施层                               │
│  ┌─────────┐ ┌─────────┐ ┌──────┐ ┌────────┐               │
│  │Category │ │ Skills  │ │Hooks │ │  MCPs  │               │
│  │系统     │ │系统     │ │系统  │ │集成    │               │
│  └─────────┘ └─────────┘ └──────┘ └────────┘               │
└─────────────────────────────────────────────────────────────┘
```

### 4.3 Category + Skill 双重机制

#### Category：任务类型分类

Category 决定"**这是什么类型的工作？**"

| Category | 默认模型 | 用途 |
|----------|----------|------|
| `visual-engineering` | Gemini 3 Pro | 前端、UI/UX、设计、动画 |
| `ultrabrain` | GPT-5.3 Codex (xhigh) | 深度逻辑推理、复杂架构 |
| `deep` | GPT-5.3 Codex (medium) | 自主问题解决，需要深入理解 |
| `artistry` | Gemini 3 Pro (max) | 高度创意和艺术任务 |
| `quick` | Claude Haiku 4.5 | 简单任务、单文件修改 |
| `writing` | Kimi K2P5 | 文档、技术写作 |

#### Skill：专业能力注入

Skill 决定"**需要什么工具和知识？**"

| Skill | 触发条件 | 作用 |
|-------|----------|------|
| `git-master` | Git 操作 | 提交架构、变骨手术、历史考古 |
| `frontend-ui-ux` | UI/UX 任务 | 设计师级前端实现 |
| `playwright` | 浏览器任务 | 浏览器自动化、测试、截图 |

**组合示例**：

```
# 设计师 (UI 实现)
Category: visual-engineering
load_skills: ["frontend-ui-ux", "playwright"]
效果：实现美观 UI 并在浏览器中验证

# 架构师 (设计审查)
Category: ultrabrain
load_skills: []
效果：利用 GPT-5.3 的深度推理能力进行架构分析

# 维护者 (快速修复)
Category: quick
load_skills: ["git-master"]
效果：使用低成本模型快速修复代码并生成提交
```

### 4.4 Hooks：44 个拦截点

Hooks 在关键节点拦截和修改行为，共 5 个层级、44 个钩子。

#### Hook 事件类型

| 事件 | 触发时机 | 能力 |
|------|----------|------|
| **PreToolUse** | 工具执行前 | 阻止、修改输入、注入上下文 |
| **PostToolUse** | 工具执行后 | 添加警告、修改输出 |
| **Message** | 消息处理中 | 转换内容、检测关键词、激活模式 |
| **Event** | 会话生命周期变化 | 恢复、回退、通知 |
| **Transform** | 上下文转换时 | 注入上下文、验证块 |
| **Params** | 设置 API 参数时 | 调整模型设置、努力级别 |

#### 关键 Hooks

**上下文注入**：
- `directory-agents-injector` - 自动注入 AGENTS.md
- `directory-readme-injector` - 自动注入 README.md
- `rules-injector` - 条件规则注入

**模式激活**：
- `keyword-detector` - 检测关键词激活模式（ultrawork、search、analyze）
- `think-mode` - 自动检测扩展思考需求
- `ralph-loop` - 自循环继续管理

**质量控制**：
- `comment-checker` - 提醒减少过多注释
- `thinking-block-validator` - 验证思考块防止 API 错误
- `edit-error-recovery` - 从编辑工具失败中恢复

**恢复与稳定性**：
- `session-recovery` - 从会话错误中恢复
- `runtime-fallback` - 自动切换到备用模型
- `model-fallback` - 管理模型回退链

### 4.5 工具体系

#### 代码搜索工具
- **grep** - 正则表达式内容搜索
- **glob** - 快速文件模式匹配

#### 编辑工具
- **edit** - 哈希锚定编辑工具（LINE#ID 格式）

#### LSP 工具（IDE 级别功能）
- **lsp_diagnostics** - 构建前获取错误/警告
- **lsp_rename** - 跨工作区重命名符号
- **lsp_goto_definition** - 跳转到符号定义
- **lsp_find_references** - 查找所有使用

#### AST-Grep 工具
- **ast_grep_search** - AST 感知代码模式搜索（支持 25 种语言）
- **ast_grep_replace** - AST 感知代码替换

#### 委托工具
- **call_omo_agent** - 生成 explore/librarian agents
- **task** - 基于 Category 的任务委托
- **background_output** - 获取后台任务结果

#### 视觉分析工具
- **look_at** - 分析媒体文件（PDF、图片、图表）

#### 技能工具
- **skill** - 加载和执行技能
- **skill_mcp** - 调用技能嵌入的 MCP 操作

### 4.6 MCP 集成

#### 内置 MCP

| MCP | 描述 |
|-----|------|
| **websearch** | 由 Exa AI 驱动的实时网络搜索 |
| **context7** | 任何库/框架的官方文档查找 |
| **grep_app** | 超快速代码搜索，覆盖公共 GitHub 仓库 |

#### 技能嵌入式 MCP

技能可以携带自己的 MCP 服务器：

```yaml
---
description: 浏览器自动化技能
mcp:
  playwright:
    command: bunx
    args: ["-y", "@anthropic-ai/mcp-playwright"]
---
```

#### OAuth 支持

支持完整的 OAuth 2.1（RFC 9728、8414、8707、7591）：
- 自动发现授权服务器
- 动态客户端注册
- 强制 PKCE
- 资源指示器
- 自动令牌刷新

### 4.7 后台执行系统

#### 可视化多 Agent（Tmux）

启用 `tmux.enabled` 在独立窗格中查看后台 Agent：

```json
{
  "tmux": {
    "enabled": true,
    "layout": "main-vertical"
  }
}
```

**特性**：
- 后台 Agent 在新窗格中启动
- 实时观察多个 Agent 工作
- 每个窗格显示 Agent 输出
- Agent 完成时自动清理

---

## 五、Agent 系统

### 5.1 核心 Agent

| Agent | 模型 | 职责 |
|-------|------|------|
| **Sisyphus** | Claude Opus 4.6 | 默认编排者。计划、委派、执行复杂任务，使用专业子 Agent 并行执行。Todo 驱动工作流。 |
| **Oracle** | GPT-5.3 | 架构决策、代码审查、调试。只读咨询，强大逻辑推理和深度分析。 |
| **Librarian** | Claude Sonnet 4.5 | 多仓库分析、文档查询、开源实现示例。深度代码库理解，基于证据的回答。 |
| **Explore** | Claude Haiku 4.5 | 快速代码库探索和上下文 grep。 |

### 4.2 计划系统 Agent

| Agent | 模型 | 职责 |
|-------|------|------|
| **Prometheus** | Claude Opus 4.6 | 计划专家。创建详细、可执行的工作计划 |
| **Metis** | Claude Opus 4.6 | 预计划顾问。分析需求、识别歧义、提出关键问题 |
| **Momus** | GPT-4.1 | 计划审查。评估计划完整性、发现遗漏、提出改进 |
| **Atlas** | Kimi K2 | 执行者。执行 Prometheus 创建的计划 |

### 4.3 Agent 切换：@ 指令 vs Tab 键

**共同点**：两者本质上都是切换 Agent。

| 方式 | 用法 | 示例 |
|------|------|------|
| **@agent** | 在对话中使用指令切换到任意 Agent | `@agent oracle 请分析这个架构` |
| **Tab 键** | 按 Tab 进入 Prometheus 模式 | 在 Sisyphus 中按 Tab → 进入 Prometheus 计划模式 |

**核心区别**：

| 特性 | @agent | Tab |
|------|--------|-----|
| **作用** | 切换到任意 Agent | 固定进入 Prometheus |
| **灵活性** | 可切换到任何 Agent | 仅用于计划模式 |
| **使用场景** | 需要特定 Agent 的专长 | 需要详细计划 |

### 4.4 常用 @agent 指令

```
@agent sisyphus    # 默认编排者
@agent prometheus  # 计划专家
@agent metis       # 预计划顾问
@agent momus       # 计划审查
@agent atlas       # 执行者
@agent oracle      # 架构咨询
@agent explore     # 代码探索
@agent librarian   # 文档查询
@agent hephaestus  # 深度工作者
```

---

## 五、计划系统详解

### 5.1 架构概览

```
用户需求
   ↓
Metis (预计划顾问) → 分析需求、识别歧义、提出问题
   ↓
Prometheus (计划专家) → 创建详细工作计划
   ↓
Momus (计划审查) → 评估计划完整性、发现遗漏
   ↓
Atlas (执行者) → 执行计划
```

### 5.2 Prometheus - 计划专家

| 属性 | 说明 |
|------|------|
| **模型** | Claude Opus 4.6 |
| **职责** | 创建详细、可执行的工作计划 |
| **输出** | `.sisyphus/plans/{任务名}.md` 计划文件 |
| **触发方式** | `@plan` 指令 或 按 **Tab** 键 |

**使用示例**：
```
@plan 构建一个用户认证系统，支持 JWT 和刷新令牌
```

### 5.3 Metis - 预计划顾问

| 属性 | 说明 |
|------|------|
| **模型** | Claude Opus 4.6 |
| **职责** | 在计划前分析需求、识别歧义、提出关键问题 |
| **作用** | 避免计划偏离真实需求 |
| **触发方式** | 自动触发（复杂任务时） |

**Metis 可能会问**：
- "JWT 过期时间应该设置为多少？"
- "是否需要支持多设备登录？"
- "刷新令牌的存储策略是什么？"
- "是否需要支持社交账号登录？"

### 5.4 Momus - 计划审查

| 属性 | 说明 |
|------|------|
| **模型** | GPT-4.1 |
| **职责** | 评估计划的完整性、发现遗漏、提出改进建议 |
| **作用** | 确保计划质量，避免执行中返工 |
| **触发方式** | Prometheus 创建计划后自动触发 |

**Momus 审查重点**：
- 计划是否覆盖所有需求
- 步骤是否可执行
- 是否有遗漏的边界情况
- 技术选型是否合理
- 是否考虑了错误处理

### 5.5 计划文件示例

`.sisyphus/plans/user-auth-system.md`：

```markdown
# 用户认证系统实现计划

## 目标
构建支持 JWT 和刷新令牌的用户认证系统

## 技术栈
- 后端: Node.js + Express
- 数据库: PostgreSQL
- 认证: JWT + Refresh Token
- 密码加密: bcrypt

## 实施步骤

### Phase 1: 数据模型
- [ ] 创建 users 表（id, email, password_hash, created_at）
- [ ] 创建 refresh_tokens 表（id, user_id, token, expires_at）
- [ ] 定义索引和约束
- [ ] 编写数据库迁移脚本

### Phase 2: 认证 API
- [ ] POST /auth/register - 用户注册
- [ ] POST /auth/login - 用户登录
- [ ] POST /auth/refresh - 刷新访问令牌
- [ ] POST /auth/logout - 登出

### Phase 3: 中间件
- [ ] JWT 验证中间件
- [ ] 刷新令牌验证中间件
- [ ] 错误处理中间件

### Phase 4: 测试
- [ ] 单元测试
- [ ] 集成测试
- [ ] 安全测试

## 注意事项
- 密码必须使用 bcrypt 加密
- JWT 密钥使用环境变量
- 刷新令牌设置合理过期时间
- 实现速率限制防止暴力破解
```

---

## 六、工作模式

### 6.1 决策流程

```
是简单的快速修复吗？
 └─ YES → 直接提示即可
 └─ NO → 解释完整上下文是否繁琐？
 └─ YES → 使用 "ulw" 让 Agent 自己搞清楚
 └─ NO → 需要精确、可验证的执行吗？
 └─ YES → 使用 @plan 进行 Prometheus 计划，然后 /start-work
 └─ NO → 使用 "ulw"
```

### 6.2 Ultrawork 全自动模式

#### 触发方式

```
ultrawork
# 或简写
ulw
```

#### 工作原理

Ultrawork 是 OMO 的"一键激活所有特性"机制。

| 特性 | 说明 |
|------|------|
| **并行后台任务** | 同时启动多个探索/Librarian Agent |
| **深度探索** | Explore Agent 深度分析代码库 |
| **强制完成** | 持续工作直到任务完成，不中途停止 |
| **多 Agent 协作** | 自动调度最合适的 Agent |
| **激进执行** | 最大化并行，最小化等待 |

#### 核心设计原则

| 原则 | 含义 |
|------|------|
| **人工干预是失败信号** | 如果需要不断修正 AI 输出，说明系统设计有问题 |
| **代码不可区分** | AI 生成的代码应与资深工程师手写无异 |
| **完成是唯一标准** | 不完成 = 失败，没有"部分完成" |

#### 使用场景

```
# ✅ 适合 Ultrawork 的场景
ulw 重构整个认证流程，使其更安全
ulw 为项目添加完整的测试覆盖
ulw 迁移数据库架构
ulw 实现用户订阅系统

# ❌ 不适合 Ultrawork 的场景
# 简单任务：直接说即可
修复这个函数的 bug

# 需要精确控制：使用 @plan
@plan 构建一个 REST API
```

### 6.3 Ralph-Loop 自循环模式

#### 两种模式对比

| 命令 | Ultrawork 模式 | 说明 |
|------|----------------|------|
| `/ralph-loop` | ❌ 关闭 | 标准自循环 |
| `/ulw-loop` | ✅ 激活 | Ultrawork 自循环 |

#### 共同点

两者使用**完全相同的循环机制**——Ralph-loop Hook。

#### 区别对比

| 特性 | /ralph-loop | /ulw-loop |
|------|-------------|-----------|
| **核心机制** | Ralph-loop hook | Ralph-loop hook（相同） |
| **Ultrawork** | 关闭 | **激活** |
| **Agent 强度** | 标准探索执行 | 最大：并行 Agent、激进探索、后台任务 |
| **继续提示前缀** | 无 | `ultrawork` 关键词 |
| **完成提示** | "Ralph Loop Complete" | "Ultrawork Loop Complete" |

#### 取消循环

```
/cancel-ralph
```

#### 使用场景

```
# 需要持续迭代直到完成
/ralph-loop 优化这个函数的性能
/ulw-loop 构建完整的用户系统（更强力）
/ralph-loop 重构这段代码，使其更简洁
```

### 6.4 /start-work 命令详解

#### 基本用法

```
/start-work
```

#### 参数说明

| 参数 | 说明 | 默认值 |
|------|------|--------|
| `--plan` | 指定计划文件路径 | 自动查找最新计划 |
| `--agent` | 指定执行 Agent | atlas |
| `--continue` | 从断点继续执行 | false |
| `--force` | 强制重新开始 | false |

#### 自动查找机制

`/start-work` 会按以下顺序查找计划文件：

1. **指定路径**：`/start-work --plan .sisyphus/plans/xxx.md`
2. **最新计划**：`.sisyphus/plans/` 目录中最近修改的计划
3. **Prometheus 输出**：最后一次 Prometheus 创建的计划

#### 完整流程示例

```
用户: @plan 构建用户认证系统
       ↓
[Metis] 您的多设备登录策略是什么？
       ↓
用户: 单设备登录，新设备踢出旧设备
       ↓
[Prometheus] 创建计划 → .sisyphus/plans/user-auth.md
       ↓
[Momus] 计划审查通过 ✅
       ↓
用户: /start-work
       ↓
[Atlas] 执行计划 → 完成任务
```

---

## 七、文件引用与搜索

### 7.1 @file 文件引用

#### 基本用法

```
@file src/main.ts
```

#### 指定行号范围

```
@file src/api/auth.ts#L45-80
```

#### 组合使用

```
@file src/config.ts
@agent oracle 这个配置结构合理吗？

@file src/components/Button.tsx#L20-50
这段状态管理逻辑有问题吗？
```

### 7.2 @workspace 工作区搜索

```
@workspace TODO
@workspace useState
@workspace FIXME
```

### 7.3 常用引用组合

| 场景 | 命令 |
|------|------|
| 解释文件 | `@file src/utils/helper.ts 解释这个工具函数` |
| 审查代码 | `@file src/api/auth.ts#L50-100 @agent oracle 这段代码安全吗？` |
| 搜索模式 | `@workspace useEffect 找出所有使用 useEffect 的文件` |
| 对比文件 | `@file src/v1/api.ts 和 @file src/v2/api.ts 有什么区别` |

---

## 八、自定义命令

### 8.1 创建 Command

#### 方式一：Markdown 文件

`.opencode/commands/test.md`：

```markdown
---
description: 运行测试并生成覆盖率报告
agent: sisyphus
model: zhipuai/glm-4.7
---

运行完整的测试套件，生成覆盖率报告，并分析失败的测试用例。
重点关注以下方面：
1. 失败原因分析
2. 覆盖率缺口
3. 修复建议
4. 性能瓶颈识别
```

使用：`/test`

#### 方式二：JSON 配置

`~/.config/opencode/opencode.json`：

```json
{
  "$schema": "https://opencode.ai/config.json",
  "commands": {
    "test": {
      "template": "运行测试并分析失败原因",
      "description": "运行测试套件",
      "agent": "sisyphus",
      "model": "zhipuai/glm-4.7"
    },
    "review": {
      "template": "审查当前代码，关注安全性、性能和可维护性",
      "description": "代码审查",
      "agent": "oracle",
      "model": "openai/gpt-4.1"
    }
  }
}
```

### 8.2 参数传递

#### 使用 $ARGUMENTS

```markdown
---
description: 创建 React 组件
---

创建一个名为 $1 的 React 组件，使用 TypeScript，
包含以下功能：$2
```

使用：
```
/component Button 支持点击加载状态
```

#### 使用位置参数

```markdown
---
description: 创建文件
---

在目录 $2 中创建文件 $1，内容如下：
$3
```

使用：
```
/create-file config.ts src "export const API_URL = 'https://api.example.com'"
```

### 8.3 Shell 命令输出

```markdown
---
description: 分析当前分支
---

当前分支是：!`git branch --show-current`
最近的提交：!`git log -1 --pretty=format:"%h - %s"`
```

### 8.4 命令位置优先级

| 位置 | 优先级 |
|------|--------|
| `{project}/.opencode/commands/` | 最高 |
| `~/.config/opencode/commands/` | 中等 |
| 配置文件中的 commands | 最低 |

---

## 九、模型切换

### 9.1 /models 命令

```
/models
```

然后在列表中选择：
```
┌  Select Model
│
◆  zhipuai/glm-4.7
│  anthropic/claude-opus-4-6
│  openai/gpt-4.1
│  moonshot/kimi-k2
│  ...
```

### 9.2 为什么切换模型？

| 任务类型 | 推荐模型 | 原因 |
|----------|----------|------|
| **编排/规划** | Claude Opus 4.6 | 逻辑推理强，长上下文 |
| **深度代码** | GPT-4.1 / GPT-5.2 | 代码生成质量高 |
| **快速探索** | Claude Haiku 4.5 | 速度快、成本低 |
| **中文任务** | GLM-4.7 | 中文理解优秀 |
| **执行计划** | Kimi K2 | 稳定可靠 |

### 9.3 Agent 默认模型配置

`.opencode/oh-my-opencode.json`：

```json
{
  "agents": {
    "sisyphus": {
      "model": "zhipuai/glm-4.7"
    },
    "prometheus": {
      "model": "anthropic/claude-opus-4-6"
    },
    "metis": {
      "model": "anthropic/claude-opus-4-6"
    },
    "momus": {
      "model": "openai/gpt-4.1"
    },
    "atlas": {
      "model": "moonshot/kimi-k2"
    },
    "oracle": {
      "model": "openai/gpt-4.1"
    },
    "explore": {
      "model": "anthropic/claude-haiku-4-5"
    },
    "librarian": {
      "model": "anthropic/claude-sonnet-4-5"
    }
  }
}
```

### 9.4 模型回退链

Oh My OpenCode 为每个 Agent 配置了模型回退链，当主模型不可用时会自动尝试备用模型：

```
Sisyphus: claude-opus-4-6 → gpt-5.2 → deep quality chain
Oracle: gpt-5.2 → claude-opus-4-6-thinking → deep quality chain
Librarian: claude-sonnet-4-5 → claude-haiku-4-5 → speed chain
Explore: claude-haiku-4-5 → gpt-4.1-mini → extended speed chain
```

---

## 十、完整工作流示例

### 10.1 场景：构建用户认证系统

#### 方式一：全自动 Ultrawork

```
ulw 构建用户认证系统，支持 JWT 和刷新令牌，单设备登录
```

**流程**：
1. 自动并行探索代码库
2. 研究最佳实践
3. 创建实现计划
4. 执行并验证
5. 持续工作直到完成

#### 方式二：精确控制

```
@plan 构建用户认证系统，支持 JWT 和刷新令牌

[Metis] 您的多设备登录策略是什么？
用户: 单设备登录，新设备踢出旧设备

[Metis] JWT 过期时间多长？
用户: 15 分钟

[Prometheus] 创建计划...
[Momus] 计划审查通过 ✅

/start-work

[Atlas] 执行计划...
```

#### 方式三：持续迭代

```
/ulw-loop 构建用户认证系统
```

会持续工作直到完成，自动处理错误和重试。

### 10.2 场景：代码审查

```
@file src/api/auth.ts
@agent oracle 请审查这段代码的安全性、性能和可维护性
```

### 10.3 场景：重构代码

```
@file src/utils/helpers.ts#L50-100
重构这些函数，使其更简洁、更易测试
```

### 10.4 场景：探索代码库

```
@workspace 找出所有使用 useState 但缺少依赖项的 useEffect
```

### 10.5 场景：添加测试

```
ulw 为 src/components/Button.tsx 添加完整的单元测试
```

---

## 十一、常用命令速查

### 11.1 OpenCode 原生命令

| 命令 | 说明 |
|------|------|
| `/help` | 显示帮助 |
| `/new` / `/clear` | 开始新会话 |
| `/models` | 选择模型 |
| `/sessions` | 列出和切换会话 |
| `/share` | 创建分享链接 |
| `/export` | 导出对话为 Markdown |
| `/exit` / `/quit` / `/q` | 退出 OpenCode |

### 11.2 Oh My OpenCode 命令

| 命令 | 说明 |
|------|------|
| `/start-work` | 执行 Prometheus 计划 |
| `/init-deep` | 初始化 AGENTS.md 知识库 |
| `/ralph-loop` | 启动标准自循环 |
| `/ulw-loop` | 启动 Ultrawork 自循环 |
| `/cancel-ralph` | 取消自循环 |
| `/stop-continuation` | 停止所有继续机制 |

### 11.3 Agent 切换

| 方式 | 说明 |
|------|------|
| `@agent sisyphus` | 切换到默认编排者 |
| `@agent prometheus` | 切换到计划专家 |
| `@agent oracle` | 切换到架构顾问 |
| `@agent explore` | 切换到代码探索 |
| `@agent librarian` | 切换到文档查询 |
| **Tab 键** | 进入 Prometheus 计划模式 |

### 11.4 特殊指令

| 指令 | 说明 |
|------|------|
| `@plan` | 触发 Prometheus 计划 |
| `@file path` | 引用文件 |
| `@workspace keyword` | 搜索工作区 |
| `ulw` / `ultrawork` | 激活 Ultrawork 模式 |

---

## 十二、配置最佳实践

### 12.1 全局配置

`~/.config/opencode/opencode.json`：

```json
{
  "$schema": "https://opencode.ai/config.json",
  "model": "zhipuai/glm-4.7",
  "theme": "opencode",
  "autoupdate": true
}
```

### 12.2 项目配置

`{project}/.opencode/oh-my-opencode.json`：

```json
{
  "agents": {
    "sisyphus": {
      "model": "zhipuai/glm-4.7"
    }
  }
}
```

### 12.3 自定义命令

`.opencode/commands/review.md`：

```markdown
---
description: 代码审查
agent: oracle
---

审查当前代码，关注：
1. 安全性
2. 性能
3. 可维护性
4. 错误处理
```

---

## 十三、故障排除

### 13.1 常见问题

**Q: OpenCode 版本过低导致配置不生效**
```bash
opencode --version  # 确保 >= 1.0.133
```

**Q: Agent 没有响应**
```bash
/opencode
# 检查 Provider 配置和 API Key
```

**Q: 计划文件找不到**
```bash
# 检查 .sisyphus/plans/ 目录
ls -la .sisyphus/plans/
```

### 13.2 获取帮助

- [GitHub Issues](https://github.com/code-yeongyu/oh-my-opencode/issues)
- [Discord 社区](https://discord.gg/oh-my-opencode)
- [官方文档](https://github.com/code-yeongyu/oh-my-opencode/blob/dev/docs/guide/overview.md)

---

## 附录：完整配置示例

### A. 全局 OpenCode 配置

`~/.config/opencode/opencode.json`：

```json
{
  "$schema": "https://opencode.ai/config.json",
  "model": "zhipuai/glm-4.7",
  "theme": "opencode",
  "autoupdate": true,
  "provider": {
    "zhipuai": {
      "npm": "@ai-sdk/openai-compatible",
      "name": "Zhipuai Coding Plan",
      "options": {
        "baseURL": "https://open.bigmodel.cn/api/paas/v4",
        "headers": {
          "Authorization": "Bearer YOUR_API_KEY_HERE"
        }
      }
    }
  }
}
```

### B. 项目 OMO 配置

`.opencode/oh-my-opencode.json`：

```json
{
  "agents": {
    "sisyphus": {
      "model": "zhipuai/glm-4.7"
    },
    "prometheus": {
      "model": "anthropic/claude-opus-4-6"
    },
    "metis": {
      "model": "anthropic/claude-opus-4-6"
    },
    "momus": {
      "model": "openai/gpt-4.1"
    },
    "atlas": {
      "model": "moonshot/kimi-k2"
    }
  }
}
```

### C. 自定义命令示例

`.opencode/commands/test.md`：

```markdown
---
description: 运行测试套件
agent: sisyphus
model: zhipuai/glm-4.7
---

运行完整的测试套件，生成覆盖率报告。
重点关注失败的测试用例，分析原因并提供修复建议。
```

`.opencode/commands/review.md`：

```markdown
---
description: 代码安全审查
agent: oracle
model: openai/gpt-4.1
---

对当前代码进行安全审查，包括：
1. SQL 注入风险
2. XSS 攻击风险
3. 认证授权问题
4. 敏感数据泄露
5. 依赖安全性
```

---

**🤖 文档生成**: GLM-4.7  
**👤 任务与方案**: JyCyun
**最后更新**: 2026-02-27
**适用版本**: Oh My OpenCode v3.9.0+
