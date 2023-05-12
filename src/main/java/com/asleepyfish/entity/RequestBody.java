package com.asleepyfish.entity;

import lombok.Data;

import java.util.List;

@Data
public class RequestBody {
    /**
     * 选择要使用的GPT模型
     */
    private String model = "gpt-3.5-turbo";

    /**
     * 发送的消息集合
     * 可将历史消息一同发送（双方）以实现上下文连贯
     */
    private List<Message> messages;

    /**
     * 这个参数用于指定生成文本时控制随机性的程度。范围是从 0 到 1 之间的浮点数。值越高，生成文本将越随机；值越低，生成文本将越接近于原有的文本内容。
     * 3.5中表示对生成的回答的多样性程度，通常在0.7到1.0之间取值。
     */
    private Double temperature;

    /**
     * 指定生成文本的最大长度。ChatGPT 将会在生成文本时尽可能地满足这个长度要求。
     * 3.5表示生成回答的最大长度，通常在50到100之间取值。
     */
    private Integer max_tokens;

    /**
     * 用于指定在生成文本时停止的条件。可以使用特定的符号作为终止符，例如 \n、.、! 等。
     */
    private String stop;

    //下3个通常默认
    /**
     * 表示尽量避免生成上下文中已经提到过的单词，通常在0.3到0.9之间取值。
     */
    private Double presence_penalty;

    /**
     * 表示尽量不重复生成同一个单词，通常在0到1之间取值。
     */
    private Double frequency_penalty;

    /**
     * 表示从概率分布中选择可能结果的阈值大小，通常在0.9到1.0之间取值
     */
    private Double top_p;

}
